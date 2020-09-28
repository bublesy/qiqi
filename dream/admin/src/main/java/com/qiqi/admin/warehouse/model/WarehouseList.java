package com.qiqi.admin.warehouse.model;

import lombok.Data;

import java.util.List;

@Data
public class WarehouseList {

    private List<Long> ids;

    private List<String> surplusNums;
}
