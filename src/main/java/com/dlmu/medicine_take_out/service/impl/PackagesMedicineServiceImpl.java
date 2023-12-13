package com.dlmu.medicine_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlmu.medicine_take_out.entity.PackagesMedicine;
import com.dlmu.medicine_take_out.mapper.PackagesMedicineMapper;
import com.dlmu.medicine_take_out.service.PackagesMedicineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PackagesMedicineServiceImpl extends ServiceImpl<PackagesMedicineMapper, PackagesMedicine> implements PackagesMedicineService {
}
