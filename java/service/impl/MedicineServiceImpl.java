package com.dlmu.medicine_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlmu.medicine_take_out.dto.MedicineDto;
import com.dlmu.medicine_take_out.entity.Medicine;
import com.dlmu.medicine_take_out.entity.MedicineRequirement;
import com.dlmu.medicine_take_out.mapper.MedicineMapper;
import com.dlmu.medicine_take_out.service.MedicineRequirementService;
import com.dlmu.medicine_take_out.service.MedicineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements MedicineService {

    @Autowired
    private MedicineRequirementService medicineRequirementService;

    /**
     * 新增菜品，同时保存对应的口味数据
     * @Transactional设计多张表的操作就开启事务注解
     */

    @Transactional
    public void saveWithFlavor(MedicineDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId=dishDto.getId();//取出菜品的id


        //为即将保存到dish_flavor表中的dish_id属性赋值
        List<MedicineRequirement> flavors=dishDto.getFlavors();
        flavors=flavors.stream().map((item)->{
            item.setMedicineId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        /**
         * saveBatch方法的参数是集合
         */
        medicineRequirementService.saveBatch(flavors);
    }


    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    public MedicineDto getByIdWithFlavor(Long id){
        //查询菜品的基本信息
        Medicine medicine =this.getById(id);

        MedicineDto dishDto=new MedicineDto();
        BeanUtils.copyProperties(medicine,dishDto);

        //查询当前菜品对应的口味信息，从dish_flavor表查询
        //LambdaQueryWrapper表示的是查询的条件
        LambdaQueryWrapper<MedicineRequirement> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicineRequirement::getMedicineId, medicine.getId());
        List<MedicineRequirement> flavors= medicineRequirementService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }


    @Override
    @Transactional
    public void updateWithFlavor(MedicineDto dishDto) {
        //当前提交过来的dishDto对象是要更新的目标
        //更新dish表对应id的菜品的基本信息
        this.updateById(dishDto);

        //清理当前菜品对应的口味数据---dish_flavor的delete操作
        LambdaQueryWrapper<MedicineRequirement> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicineRequirement::getMedicineId,dishDto.getId());

        medicineRequirementService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor的insert操作
        List<MedicineRequirement> flavors=dishDto.getFlavors();

        flavors=flavors.stream().map((item)->{
            item.setMedicineId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        /**
         * 大多都是对Service类的方法传入对象去进行更新的操作，这里的就是flavors对象
         */
        medicineRequirementService.saveBatch(flavors);

    }
}
