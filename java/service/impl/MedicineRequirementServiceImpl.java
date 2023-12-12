package com.dlmu.medicine_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlmu.medicine_take_out.entity.MedicineRequirement;
import com.dlmu.medicine_take_out.mapper.MedicineRequirementMapper;
import com.dlmu.medicine_take_out.service.MedicineRequirementService;
import org.springframework.stereotype.Service;

@Service
public class MedicineRequirementServiceImpl extends ServiceImpl<MedicineRequirementMapper, MedicineRequirement> implements MedicineRequirementService {
}
