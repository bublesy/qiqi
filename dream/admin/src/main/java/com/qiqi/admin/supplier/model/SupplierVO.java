package com.qiqi.admin.supplier.model;

import lombok.Data;

/**
 * @author sun
 * @time 2020/9/15 10:37
 */
@Data
public class SupplierVO {
    private Long id;
    /**
     * 编码
     */
    private String code;

    /**
     * 简称
     */
    private String abbreviation;

    /**
     * 全称
     */
    private String fullName;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 网址
     */
    private String website;

    /**
     * 地址
     */
    private String address;

}
