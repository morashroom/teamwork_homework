package com.dlmu.medicine_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlmu.medicine_take_out.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
