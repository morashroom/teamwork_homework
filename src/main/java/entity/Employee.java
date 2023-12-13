package com.dlmu.medicine_take_out.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;//在数据库中对应的是id_number,不过没有关系的是application.xml的配置文件中已经写了map-underscore-to-camel-case: true

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;//驼峰法

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;//驼峰法

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
