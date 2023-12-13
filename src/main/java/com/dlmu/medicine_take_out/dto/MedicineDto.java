package com.dlmu.medicine_take_out.dto;

import com.dlmu.medicine_take_out.entity.Medicine;
import com.dlmu.medicine_take_out.entity.MedicineRequirement;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;


/**
 * 对应于浏览器传递过来的数据对象
 */

@Data
public class MedicineDto extends Medicine {

    private List<MedicineRequirement> flavors = new ArrayList<>();

    private String categoryName;
    /**
     * categoryName的属性对应的是前端列表中的页面
     */

    private Integer copies;
}
