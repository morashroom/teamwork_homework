package com.dlmu.medicine_take_out.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务端返回的结果类，统一返回的是这样一个结果
 * @param <T>
 */



@Data
public class R<T> {
//    R是一个泛型的类
    /**
     * 使用泛型的作用：增强通用性
     */

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息：比如登录了但是密码写错了就会把错误的信息书写下来

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object) {
        //是泛型的方法
        //返回的类型是泛型
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;//表示的是返回成功
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
