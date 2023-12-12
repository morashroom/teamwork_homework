package com.dlmu.medicine_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlmu.medicine_take_out.common.CustomException;
import com.dlmu.medicine_take_out.entity.Category;
import com.dlmu.medicine_take_out.entity.Medicine;
import com.dlmu.medicine_take_out.entity.Packages;
import com.dlmu.medicine_take_out.mapper.CategoryMapper;
import com.dlmu.medicine_take_out.service.CategoryService;
import com.dlmu.medicine_take_out.service.MedicineService;
import com.dlmu.medicine_take_out.service.PackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private PackagesService packagesService;

    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */


    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Medicine> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件
        dishLambdaQueryWrapper.eq(Medicine::getCategoryId,id);
        int count1= medicineService.count(dishLambdaQueryWrapper);

        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if(count1>0) {
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Packages> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Packages::getCategoryId,id);
        int count2= packagesService.count(setmealLambdaQueryWrapper);

        //正常删除分类
        if(count2>0) {
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        //正常删除分类
        super.removeById(id);

    }
}
