package com.dlmu.medicine_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlmu.medicine_take_out.common.BaseContext;
import com.dlmu.medicine_take_out.entity.*;
import com.dlmu.medicine_take_out.service.*;
import com.dlmu.medicine_take_out.common.CustomException;
import com.dlmu.medicine_take_out.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;
    /**
     * 用户下单
     * @param orders
     */

    @Override
    @Transactional
    /**
     * orders里就包含有addressbookId
     */
    public void submit(Orders orders ) {
//        //获得当前用户id
//        Long userId= BaseContext.getCurrentId();
//
//        //查询当前用户的购物车数据
//        LambdaQueryWrapper<ShoppingCart> wrapper=new LambdaQueryWrapper<>();
//        wrapper.eq(ShoppingCart::getUserId,userId);
//        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);
//
//        if(shoppingCarts==null||shoppingCarts.size()==0) {
//            throw new CustomException("购物车为空，不能下单");
//        }
//
//        //查询用户数据
//        User user=userService.getById(userId);
//
//        //查询地址数据
//        Long addressBookId=orders.getAddressBookId();
//        AddressBook addressBook=addressBookService.getById(addressBookId);
//
//        if(addressBook==null) {
//            throw new CustomException("用户的地址为空，不能下单");
//        }
//
//        long orderId = IdWorker.getId();//订单号
//
//        /**
//         * AtomicInteger：原子整型，线程安全
//         */
//        AtomicInteger amount = new AtomicInteger(0);
//
//        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrderId(orderId);
//            orderDetail.setNumber(item.getNumber());
//            orderDetail.setDishFlavor(item.getDishFlavor());
//            orderDetail.setDishId(item.getDishId());
//            orderDetail.setSetmealId(item.getSetmealId());
//            orderDetail.setName(item.getName());
//            orderDetail.setImage(item.getImage());
//            orderDetail.setAmount(item.getAmount());
//            //getAmount()方法是BigDecimal类型
//            //addAngGet需要的是整数
//            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
//            return orderDetail;
//        }).collect(Collectors.toList());
//
//
//        orders.setId(orderId);
//        orders.setOrderTime(LocalDateTime.now());
//        orders.setCheckoutTime(LocalDateTime.now());
//        orders.setStatus(2);
//        orders.setAmount(new BigDecimal(amount.get()));//总金额
//        orders.setUserId(userId);
//        orders.setNumber(String.valueOf(orderId));
//        orders.setUserName(user.getName());
//        orders.setConsignee(addressBook.getConsignee());
//        orders.setPhone(addressBook.getPhone());
//        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
//                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
//                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
//                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
//        //向订单表插入数据，一条数据
//        this.save(orders);
//
//        //向订单明细表插入数据，多条数据
//        orderDetailService.saveBatch(orderDetails);
//
//
//        //下单完成之后清空购物车的数据
//        shoppingCartService.remove(wrapper);


        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        //条件构造器
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //根据当前用户id查询其购物车数据
        shoppingCartLambdaQueryWrapper.eq(userId != null, ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(shoppingCartLambdaQueryWrapper);
        //判断一下购物车是否为空
        if (shoppingCarts == null) {
            throw new CustomException("购物车数据为空，不能下单");
        }
        //判断一下地址是否有误
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBookId == null) {
            throw new CustomException("地址信息有误，不能下单");
        }
        //获取用户信息，为了后面赋值
        User user = userService.getById(userId);
        long orderId = IdWorker.getId();
        AtomicInteger amount = new AtomicInteger(0);
        //向订单细节表设置属性
        List<OrderDetail> orderDetailList= shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setMedicineId(item.getMedicineId());
            orderDetail.setPackagesId(item.getPackagesId());
//            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setNumber(item.getNumber());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());

            return orderDetail;
        }).collect(Collectors.toList());

        //向订单表设置属性
        orders.setId(orderId);
        orders.setNumber(String.valueOf(orderId));
        orders.setStatus(2);
        orders.setUserId(userId);
        orders.setAddressBookId(addressBookId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setPhone(addressBook.getPhone());
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setAddress(
                (addressBook.getProvinceName() == null ? "":addressBook.getProvinceName())+
                        (addressBook.getCityName() == null ? "":addressBook.getCityName())+
                        (addressBook.getDistrictName() == null ? "":addressBook.getDistrictName())+
                        (addressBook.getDetail() == null ? "":addressBook.getDetail())
        );

        //根据查询到的购物车数据，对订单表插入数据（1条）
        super.save(orders);
        //根据查询到的购物车数据，对订单明细表插入数据（多条）
        orderDetailService.saveBatch(orderDetailList);
        //清空购物车数据
        shoppingCartService.remove(shoppingCartLambdaQueryWrapper);

    }
}
