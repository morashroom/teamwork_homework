package com.dlmu.medicine_take_out.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
药品需求
 */
@Data
/**
 * 写有@Data注解的类不需要使用set/get方法
 * Serializable的作用，当一个父类实现Serializable接口之后，其所有的子类自动实现序列化
 */
public class MedicineRequirement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //药品id
    private Long medicineId;


    //口味名称 比如说【甜度】
    private String name;


    //口味数据list 口味的程度，比如说【微甜】【少甜】【中甜】
    private String value;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;

}
