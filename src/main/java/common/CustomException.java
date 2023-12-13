package com.dlmu.medicine_take_out.common;

/**
 * 自定义业务异常类
 */

public class CustomException extends RuntimeException{
    //异常的构造器
    public CustomException(String message) {
        super(message);//异常的信息
    }
}
