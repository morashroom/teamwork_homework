package com.dlmu.medicine_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlmu.medicine_take_out.entity.Category;

/**
 * IService本身封装了大量的crud的操作
 * 在Service层里书写没有的查询操作
 */

public interface CategoryService extends IService<Category> {
    public void remove(Long id) ;
}
