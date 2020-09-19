package com.qiqi.admin.purchase.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author sun
 * @time 2020/9/15 11:45
 */
@Data
public class PurchaseOrderVO {
    private String fullName;
    private String fax;
    private String mobilePhone;
    /**
     * 供应商id
     */
    private String supplierId;

    private Boolean isProduct;

    private String taskNumber;
    /**
     * 计价方式
     */
    @TableField("pricingMethod")
    private String pricingMethod;

    /**
     * 开单日期
     */
    @TableField("billingDate")
    private LocalDateTime billingDate;

    /**
     * 交货日期
     */
    @TableField("deliveryDate")
    private LocalDateTime deliveryDate;

    /**
     * 客户id
     */
    private String customerName;

    /**
     * 楞型
     */
    @TableField("ridgeType")
    private String ridgeType;

    /**
     * 材质
     */
    private String material;

    private String documentsNo;

    /**
     * 分压规格
     */
    @TableField("parPreSpe")
    private String parPreSpe;

    /**
     * 纸长
     */
    @TableField("paperLength")
    private String paperLength;

    /**
     * 纸宽
     */
    @TableField("paperWidth")
    private String paperWidth;

    /**
     * 订单数量
     */
    @TableField("orderQuantity")
    private String orderQuantity;

    /**
     * 采购数量
     */
    @TableField("purchaseQuantity")
    private String purchaseQuantity;

    /**
     * 配料面积
     */
    private String batching;

    /**
     * 平方价
     */
    @TableField("squarePrice")
    private String squarePrice;

    /**
     * 单价
     */
    @TableField("unitPrice")
    private String unitPrice;

    /**
     * 金额
     */
    private String amount;

    /**
     * 单位
     */
    private String unit;

    /**
     * 备注
     */
    private String remark;

    private Long id;
}
