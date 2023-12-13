package com.dlmu.medicine_take_out.dto.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlmu.medicine_take_out.common.R;
import com.dlmu.medicine_take_out.dto.MedicineDto;
import com.dlmu.medicine_take_out.dto.PackagesDto;
import com.dlmu.medicine_take_out.entity.Category;
import com.dlmu.medicine_take_out.entity.Medicine;
import com.dlmu.medicine_take_out.entity.Packages;
import com.dlmu.medicine_take_out.entity.PackagesMedicine;
import com.dlmu.medicine_take_out.service.CategoryService;
import com.dlmu.medicine_take_out.service.MedicineService;
import com.dlmu.medicine_take_out.service.PackagesMedicineService;
import com.dlmu.medicine_take_out.service.PackagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */

@RestController
@RequestMapping("/packages")
@Slf4j
public class PackagesController {

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PackagesMedicineService packagesMedicineService;

    @Autowired
    private MedicineService medicineService;


    @PostMapping
    public R<String> save(@RequestBody PackagesDto setmealDto){
        log.info("套餐信息：{}",setmealDto);

        packagesService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 套餐的分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name) {

        log.info("这里进行了分页的查询");

        Page<Packages> pageInfo=new Page<>(page,pageSize);
        Page<PackagesDto> dtoPage=new Page<>();

        LambdaQueryWrapper<Packages> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.like(name!=null, Packages::getName,name);
        queryWrapper.orderByDesc(Packages::getUpdateTime);

        packagesService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Packages> records=pageInfo.getRecords();

        List<PackagesDto> list=records.stream().map((item)->{
            PackagesDto setmealDto=new PackagesDto();//这里的dto对象是一个空的
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId=item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                //分类名称
                String categoryName=category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            /**
             * 所有stream流去赋值的最终都会需要进行结果返回的
             */
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return  R.success(dtoPage);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        //接收的是ids的集合

        packagesService.removeWithDish(ids);

        return R.success("套餐数据删除成功");
    }


    /**
     * key-value直接就是使用实体去接收就可以了
     * @param packages
     * @return
     */
    @GetMapping("/list")
    public R<List<Packages>> list(Packages packages){
        LambdaQueryWrapper<Packages> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(packages.getCategoryId()!=null, Packages::getCategoryId, packages.getCategoryId());
        queryWrapper.eq(packages.getStatus()!=null, Packages::getStatus, packages.getStatus());

        queryWrapper.orderByDesc(Packages::getUpdateTime);

        List<Packages> list= packagesService.list(queryWrapper);


        return R.success(list);
    }

    @GetMapping("/medicine/{id}")
    public R<List<MedicineDto>> showSetmealDish(@PathVariable Long id) {
        //条件构造器
        LambdaQueryWrapper<PackagesMedicine> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        dishLambdaQueryWrapper.eq(PackagesMedicine::getPackagesId, id);
        //查询数据
        List<PackagesMedicine> records = packagesMedicineService.list(dishLambdaQueryWrapper);
        List<MedicineDto> dtoList = records.stream().map((item) -> {
            MedicineDto dishDto = new MedicineDto();
            //copy数据
            BeanUtils.copyProperties(item,dishDto);
            //查询对应药品id
            Long dishId = item.getMedicineId();
            //根据药品id获取具体药品数据，这里要自动装配 dishService
            Medicine medicine = medicineService.getById(dishId);
            //其实主要数据是要那个图片，不过我们这里多copy一点也没事
            BeanUtils.copyProperties(medicine,dishDto);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(dtoList);
    }


    /**
     * 套餐数据的回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<PackagesDto> getById(@PathVariable Long id) {
        Packages packages = packagesService.getById(id);
        PackagesDto setmealDto = new PackagesDto();
        //拷贝数据
        BeanUtils.copyProperties(packages, setmealDto);
        //条件构造器
        LambdaQueryWrapper<PackagesMedicine> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(PackagesMedicine::getPackagesId, id);
        List<PackagesMedicine> packagesMedicines = packagesMedicineService.list(queryWrapper);
        //然后再设置属性
        setmealDto.setPackagesMedicines(packagesMedicines);
        //作为结果返回
        return R.success(setmealDto);
    }

    /**
     * 修改套餐的信息
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<Packages> updateWithDish(@RequestBody PackagesDto setmealDto) {
        List<PackagesMedicine> packagesMedicines = setmealDto.getPackagesMedicines();
        Long setmealId = setmealDto.getId();
        //先根据id把packagesDish表中对应套餐的数据删了
        LambdaQueryWrapper<PackagesMedicine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PackagesMedicine::getPackagesId,setmealId);
        packagesMedicineService.remove(queryWrapper);
        //然后在重新添加
        packagesMedicines = packagesMedicines.stream().map((item) ->{
            //这属性没有，需要我们手动设置一下
            item.setPackagesId(setmealId);
            return item;
        }).collect(Collectors.toList());
        //更新套餐数据
        packagesService.updateById(setmealDto);
        //更新套餐对应药品数据
        packagesMedicineService.saveBatch(packagesMedicines);
        return R.success(setmealDto);
    }

}
