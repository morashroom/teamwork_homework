package com.dlmu.medicine_take_out.entity;

import lombok.Data;

@Data
public class HealthTab {

    private static final long serialVersionUID = 1L;//表示的是主键

    private Long id;

    //姓名
    private String name;

    //电话
    private String telephone;

    private Long temperature;

    private Long status;

    private boolean isTouchriskpeople;

    private boolean isTouch;

    private boolean isTouchoverseas;

}
