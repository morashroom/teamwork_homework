package com.dlmu.medicine_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlmu.medicine_take_out.dto.MedicineDto;
import com.dlmu.medicine_take_out.entity.Medicine;


public interface MedicineService extends IService<Medicine> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish,dish_flavor
    public void saveWithFlavor(MedicineDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public MedicineDto getByIdWithFlavor(Long id);

    //更新菜品信息同时更新口味信息
    public void updateWithFlavor(MedicineDto dishDto);
}
