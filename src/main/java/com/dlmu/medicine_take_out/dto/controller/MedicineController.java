package com.dlmu.medicine_take_out.dto.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlmu.medicine_take_out.common.CustomException;
import com.dlmu.medicine_take_out.common.R;
import com.dlmu.medicine_take_out.dto.MedicineDto;
import com.dlmu.medicine_take_out.entity.Category;
import com.dlmu.medicine_take_out.entity.Medicine;
import com.dlmu.medicine_take_out.entity.MedicineRequirement;
import com.dlmu.medicine_take_out.service.CategoryService;
import com.dlmu.medicine_take_out.service.MedicineRequirementService;
import com.dlmu.medicine_take_out.service.MedicineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 药品管理，每一项每一项药品的管理
 */

@RestController
@RequestMapping("/medicine")
@Slf4j

public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineRequirementService medicineRequirementService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody MedicineDto dishDto) {

        medicineService.saveWithFlavor(dishDto);
        return R.success("新增药品成功");
    }

    /**
     * 药品信息的分页
     * Page的对象里面是包含有records的属性就是分页表中显示的一条一条的数据项
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name) {
        //构造分页构造器
        Page<Medicine> pageInfo=new Page<>(page,pageSize);
        Page<MedicineDto> dishDtoPage=new Page<>();

        //条件构造器
        LambdaQueryWrapper<Medicine> queryWrapper=new LambdaQueryWrapper<>();

        //添加过滤条件
        queryWrapper.like(name!=null, Medicine::getName,name);

        //添加排序条件
        queryWrapper.orderByDesc(Medicine::getUpdateTime);

        //执行分页查询
        medicineService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        //药品的list集合
        List<Medicine> records=pageInfo.getRecords();


        //前端页面的药品的DishDto的集合
        List<MedicineDto> list= records.stream().map((item)->{
            MedicineDto dishDto=new MedicineDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId=item.getCategoryId();//获取分类主键的id
            //根据id查询分类
            Category category=categoryService.getById(categoryId);

            if(category!=null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询药品信息和对应的口味信息
     * 既要查药品信息又要查口味信息，查询两张表
     * 查询的结果封装到DishDto里去
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<MedicineDto> get(@PathVariable Long id){
        MedicineDto dishDto= medicineService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }


    @PutMapping
    public R<String> update(@RequestBody MedicineDto dishDto) {

        medicineService.updateWithFlavor(dishDto);
        return R.success("新增药品成功");
    }


    /**
     * 根据条件查询对应的药品数据
     * @param medicine
     * @return
     */
    @GetMapping("/list")
    //这里的花主要是要接收categoryId这个数据
    public R<List<MedicineDto>> list(Medicine medicine){

        //构造查询条件
        LambdaQueryWrapper<Medicine> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(medicine.getCategoryId()!=null, Medicine::getCategoryId, medicine.getCategoryId());
        //添加条件，查询状态为1的药品(1是
        queryWrapper.eq(Medicine::getStatus,1);


        //添加排序条件
        queryWrapper.orderByDesc(Medicine::getSort).orderByDesc(Medicine::getUpdateTime);


        List<Medicine> list= medicineService.list(queryWrapper);

        List<MedicineDto> dishDtoList= list.stream().map((item)->{
            MedicineDto dishDto=new MedicineDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId=item.getCategoryId();//获取分类主键的id
            //根据id查询分类
            Category category=categoryService.getById(categoryId);

            if(category!=null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前药品的id
            Long dishId=item.getId();
            LambdaQueryWrapper<MedicineRequirement> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(MedicineRequirement::getMedicineId,dishId);

            List<MedicineRequirement> dishFlavorList=medicineRequirementService.list(lambdaQueryWrapper);
//            List<MedicineRequirement> dishFlavorList=MedicineRequirementService.;
            dishDto.setFlavors(dishFlavorList);

            return dishDto;
        }).collect(Collectors.toList());


        return R.success(dishDtoList);
    }

    //启售和停售
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status, Long ids) {
        log.info("status:{},ids:{}", status, ids);
        Medicine medicine = medicineService.getById(ids);
        if (medicine != null) {
            //直接用它传进来的这个status改就行
            medicine.setStatus(status);
            medicineService.updateById(medicine);
            return R.success("售卖状态修改成功");
        }
        return R.error("系统繁忙，请稍后再试");
    }

    //药品的批量删除
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("删除的ids：{}", ids);
        LambdaQueryWrapper<Medicine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Medicine::getId, ids);
        List<Medicine> medicines = medicineService.list(queryWrapper);
        for (Medicine medicine : medicines) {
            if (medicine.getStatus() == 1) {
                throw new CustomException("删除列表中存在启售状态商品，无法删除");
            }
        }
        medicineService.remove(queryWrapper);
        return R.success("删除成功");
    }





}
