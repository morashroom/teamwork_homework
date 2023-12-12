package com.dlmu.medicine_take_out.dto.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dlmu.medicine_take_out.common.BaseContext;
import com.dlmu.medicine_take_out.entity.ShoppingCart;
import com.dlmu.medicine_take_out.service.ShoppingCartService;
import com.dlmu.medicine_take_out.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")

public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车数据：{}",shoppingCart);

        //设置用户ID
        Long currentId= BaseContext.getCurrentId();
        log.info("用户的id是：{}",currentId);
        shoppingCart.setUserId(currentId);

        //查询当前药品或套餐是否在购物车中
        Long dishId=shoppingCart.getMedicineId();

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,currentId);

        if(dishId!=null) {
            //添加到购物车的药品是
            lambdaQueryWrapper.eq(ShoppingCart::getMedicineId,dishId);
        }else {
            lambdaQueryWrapper.eq(ShoppingCart::getPackagesId,shoppingCart.getPackagesId());
        }

        //SQL:select * from shopping_cart where user_id=? and dish_id/packages_id=?
        ShoppingCart cartServiceOne=shoppingCartService.getOne(lambdaQueryWrapper);

        //如果已经存在，就在原来数量的基础上加一
        if(cartServiceOne !=null) {
            //这里的cartServiceOne表示的是查出来的对象
            Integer number=cartServiceOne.getNumber();
            cartServiceOne.setNumber(number+1);
            log.info("购物车对象是{}",shoppingCart);
            shoppingCartService.updateById(cartServiceOne);

        }else {
            //不存在购物车的数据，则添加到购物车，数量默认是1
            /**
             * 这里的shoppingCart是前端传输过来的参数
             */
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            log.info("购物车对象是{}",shoppingCart);
            shoppingCartService.save(shoppingCart);
            cartServiceOne=shoppingCart;
        }

        return R.success(cartServiceOne);
    }


    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {

        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        //
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list=shoppingCartService.list(queryWrapper);


        return R.success(list);
    }


    /**
     * 清空购物车
     */
    @DeleteMapping("clean")
    public R<String> clean(){

        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());

        shoppingCartService.remove(queryWrapper);

        return R.success("清空购物车成功");

    }

    //购物车的减去的按钮
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        Long dishId = shoppingCart.getMedicineId();
        Long setmealId = shoppingCart.getPackagesId();
        //条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //只查询当前用户ID的购物车
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        //代表数量减少的是药品数量
        if (dishId != null) {
            //通过dishId查出购物车药品数据
            queryWrapper.eq(ShoppingCart::getMedicineId, dishId);
            ShoppingCart dishCart = shoppingCartService.getOne(queryWrapper);
            //将查出来的数据的数量-1
            dishCart.setNumber(dishCart.getNumber() - 1);
            Integer currentNum = dishCart.getNumber();
            //然后判断
            if (currentNum > 0) {
                //大于0则更新
                shoppingCartService.updateById(dishCart);
            } else if (currentNum == 0) {
                //小于0则删除
                shoppingCartService.removeById(dishCart.getId());
            }
            return R.success(dishCart);
        }

        if (setmealId != null) {
            //通过packagesId查询购物车套餐数据
            queryWrapper.eq(ShoppingCart::getPackagesId, setmealId);
            ShoppingCart setmealCart = shoppingCartService.getOne(queryWrapper);
            //将查出来的数据的数量-1
            setmealCart.setNumber(setmealCart.getNumber() - 1);
            Integer currentNum = setmealCart.getNumber();
            //然后判断
            if (currentNum > 0) {
                //大于0则更新
                shoppingCartService.updateById(setmealCart);
            } else if (currentNum == 0) {
                //等于0则删除
                shoppingCartService.removeById(setmealCart.getId());
            }
            return R.success(setmealCart);
        }
        return R.error("系统繁忙，请稍后再试");
    }

}
