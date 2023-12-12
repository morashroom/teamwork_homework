package com.dlmu.medicine_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlmu.medicine_take_out.mapper.HealthTabMapper;
import com.dlmu.medicine_take_out.service.HealthTabService;
import com.dlmu.medicine_take_out.entity.HealthTab;
import org.springframework.stereotype.Service;

@Service
public class HealthTabServiceImpl extends ServiceImpl<HealthTabMapper, HealthTab> implements HealthTabService {
}
