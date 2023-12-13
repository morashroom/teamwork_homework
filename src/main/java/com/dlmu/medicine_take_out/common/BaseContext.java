package com.dlmu.medicine_take_out.common;


/**
 * 基于ThreadLocal封装的工具类，用户保存和获取当前登录用户的id
 * 注意！！！ThreadLocal里面只能存一个东西
 * 工具类的方法都使用static字段
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
