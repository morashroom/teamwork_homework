package com.dlmu.medicine_take_out.dto.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlmu.medicine_take_out.common.BaseContext;
import com.dlmu.medicine_take_out.dto.OrderDto;
import com.dlmu.medicine_take_out.service.OrderDetailService;
import com.dlmu.medicine_take_out.service.OrderService;
import com.dlmu.medicine_take_out.common.R;
import com.dlmu.medicine_take_out.entity.OrderDetail;
import com.dlmu.medicine_take_out.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);

        return R.success("下单成功");
    }


    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize) {
        //获取当前id
        Long userId = BaseContext.getCurrentId();
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrderDto> ordersDtoPage = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //查询当前用户id订单数据
        queryWrapper.eq(userId != null, Orders::getUserId, userId);
        //按时间降序排序
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo, queryWrapper);
        List<OrderDto> list = pageInfo.getRecords().stream().map((item) -> {
            OrderDto ordersDto = new OrderDto();
            //获取orderId,然后根据这个id，去orderDetail表中查数据
            Long orderId = item.getId();
            LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrderDetail::getOrderId, orderId);
            List<OrderDetail> details = orderDetailService.list(wrapper);
            BeanUtils.copyProperties(item, ordersDto);
            //之后set一下属性
            ordersDto.setOrderDetails(details);
            return ordersDto;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(pageInfo, ordersDtoPage, "records");
        ordersDtoPage.setRecords(list);
        //日志输出看一下
        log.info("list:{}", list);
        return R.success(ordersDtoPage);
    }

    @PutMapping
    public R<String> changeStatus(@RequestBody Map<String, String> map) {
        int status = Integer.parseInt(map.get("status"));
        Long orderId = Long.valueOf(map.get("id"));
        log.info("修改订单状态:status={status},id={id}", status, orderId);
        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Orders::getId, orderId);
        updateWrapper.set(Orders::getStatus, status);
        orderService.update(updateWrapper);
        return R.success("订单状态修改成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, Long number, String beginTime, String endTime) {
        //获取当前id
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrderDto> ordersDtoPage = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //按时间降序排序
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //订单号
        queryWrapper.eq(number != null, Orders::getId, number);
        //时间段，大于开始，小于结束
        queryWrapper.gt(!StringUtils.isEmpty(beginTime), Orders::getOrderTime, beginTime)
                .lt(!StringUtils.isEmpty(endTime), Orders::getOrderTime, endTime);
        orderService.page(pageInfo, queryWrapper);
        List<OrderDto> list = pageInfo.getRecords().stream().map((item) -> {
            OrderDto ordersDto = new OrderDto();
            //获取orderId,然后根据这个id，去orderDetail表中查数据
            Long orderId = item.getId();
            LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrderDetail::getOrderId, orderId);
            List<OrderDetail> details = orderDetailService.list(wrapper);
            BeanUtils.copyProperties(item, ordersDto);
            //之后set一下属性
            ordersDto.setOrderDetails(details);
            return ordersDto;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(pageInfo, ordersDtoPage, "records");
        ordersDtoPage.setRecords(list);
        //日志输出看一下
        log.info("这里现在在进行相关的测试，list:{}", list);
        return R.success(ordersDtoPage);
    }

}
