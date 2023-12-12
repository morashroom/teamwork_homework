package com.dlmu.medicine_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlmu.medicine_take_out.entity.Employee;
import com.dlmu.medicine_take_out.mapper.EmployeeMapper;
import com.dlmu.medicine_take_out.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
//    一个是对应的实体类(对应的数据表),一个是对应的mapper层的接口

}
