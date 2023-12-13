package com.dlmu.medicine_take_out.dto.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlmu.medicine_take_out.service.HealthTabService;
import com.dlmu.medicine_take_out.common.R;
import com.dlmu.medicine_take_out.entity.HealthTab;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/healthy")
public class HealthTabController {

    @Autowired
    private HealthTabService healthTabService;

    @PostMapping("/add")
    public R<String> save(@RequestBody HealthTab healthTab) {
        log.info("员工相关的信息是{}",healthTab);
        healthTabService.save(healthTab);
        return R.success("打卡成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page= {},pageSize={},name={}",page,pageSize,name);

        //构造分页构造器,也就是new出来的Page对象
        Page pageInfo=new Page(page,pageSize);


        //构造条件构造器
        LambdaQueryWrapper<HealthTab> queryWrapper=new LambdaQueryWrapper();
        //添加过滤条件
//        queryWrapper.like(StringUtils.isNotEmpty(name),HealthTab::getName,name);
        //添加排序条件
//        queryWrapper.orderByDesc(HealthTab::getId);

        //查询完成之后就会将Page类的对象进行封装
        healthTabService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

}
