package com.qiqi.admin.basicdata.model;

import lombok.Data;

/**
 * @author sun
 * @time 2020/9/15 14:37
 */
@Data
public class SupplierCardboardQuotationVO {
    private Long id;

    private String ridgeType;

    private Long scqId;
    private String name;

    /**
     * code
     */
    private String code;
    /**
     * totalPrice
     */
    private String totalPrice;
    /**
     * pagerId
     */
    private String pagerId;
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
     * 简称
     */
    private String abbreviation;
    /**
     * 全称
     */
    private String fullName;
}
