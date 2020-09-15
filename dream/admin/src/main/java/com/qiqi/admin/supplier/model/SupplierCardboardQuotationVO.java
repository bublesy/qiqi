package com.qiqi.admin.supplier.model;

import lombok.Data;

/**
 * @author sun
 * @time 2020/9/15 14:37
 */
@Data
public class SupplierCardboardQuotationVO {
    private Long id;
    /**
     * 供应商id
     */
    private String supplierId;

    /**
     * 报价单位
     */
    private String quotationUnit;
    /**
     * 纸板报价
     */
    private String cardboardQuotation;

    /**
     * 优惠设定
     */
    private String preferentialSetting;

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
}
