package com.dlmu.medicine_take_out.dto;

import com.dlmu.medicine_take_out.entity.Packages;
import com.dlmu.medicine_take_out.entity.PackagesMedicine;
import lombok.Data;
import java.util.List;

@Data
public class PackagesDto extends Packages {

    private List<PackagesMedicine> packagesMedicines;

    private String categoryName;
}
