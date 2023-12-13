package com.dlmu.medicine_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlmu.medicine_take_out.common.CustomException;
import com.dlmu.medicine_take_out.dto.PackagesDto;
import com.dlmu.medicine_take_out.entity.Packages;
import com.dlmu.medicine_take_out.entity.PackagesMedicine;
import com.dlmu.medicine_take_out.mapper.PackagesMapper;
import com.dlmu.medicine_take_out.service.PackagesMedicineService;
import com.dlmu.medicine_take_out.service.PackagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PackagesServiceImpl extends ServiceImpl<PackagesMapper, Packages> implements PackagesService {

    @Autowired
    private PackagesMedicineService packagesMedicineService;

    @Override
    @Transactional
    public void saveWithDish(PackagesDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);


        List<PackagesMedicine> packagesMedicines =setmealDto.getPackagesMedicines();
        packagesMedicines.stream().map((item)->{
            item.setPackagesId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联信息，操作setmeal_dish，执行insert操作
        packagesMedicineService.saveBatch(packagesMedicines);


    }


    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     */
    @Transactional
    public void removeWithDish(List<Long> ids){
        //查询套餐的状态，确定是否可用删除
        LambdaQueryWrapper<Packages> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Packages::getId,ids);//查询的对象中有id在ids里面的
        queryWrapper.eq(Packages::getStatus,1);//查询的对象status的状态是1的

        int count=this.count(queryWrapper);//满足条件的数据总数
        if(count>0){
            //如果不能删除，抛出一个业务异常，这里就是查询出来了相关的目标数据
            throw new CustomException("套餐正在售卖中，不能删除");

        }

        //如果可以删除，先删除套餐表中的数据--setmeal
        this.removeByIds(ids);

        //删除关系表中的数据---setmeal_dish
        LambdaQueryWrapper<PackagesMedicine> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PackagesMedicine::getPackagesId,ids);
        //删除关系表中的数据---setmeal_dish
        packagesMedicineService.remove(lambdaQueryWrapper);


    }
}
