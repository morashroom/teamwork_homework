package com.dlmu.medicine_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlmu.medicine_take_out.dto.PackagesDto;
import com.dlmu.medicine_take_out.entity.Packages;

import java.util.List;

public interface PackagesService extends IService<Packages> {
    /**
     * 新增套餐，同时保留套餐和菜品的管理关系
     * @param setmealDto
     */
    public void saveWithDish(PackagesDto setmealDto);

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     */
    public void removeWithDish(List<Long> ids);



}
