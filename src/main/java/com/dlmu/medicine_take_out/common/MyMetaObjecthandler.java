package com.dlmu.medicine_take_out.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {

    /**
     * 执行insert添加数据的时候起的作用
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info(metaObject.toString());

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());

    }

    /**
     * 执行update更新操作的时候起作用
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info(metaObject.toString());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}
