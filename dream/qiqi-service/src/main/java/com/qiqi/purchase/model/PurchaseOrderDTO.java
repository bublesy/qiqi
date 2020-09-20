package com.qiqi.purchase.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author sun
 */
@Data
public class PurchaseOrderDTO {

    private String position;
    private String endProductPos;

    private Long orderId;
    /**
     * 供应商id
     */
    private String supplierId;

    private String settlementPeriod;

    private String boxType;
    private String modelNo;
    private String taskNumber;

    private String paperHeight;

    private Boolean isProduct;

    /**
     * 计价方式
     */
    @TableField("pricingMethod")
    private String pricingMethod;

    /**
     * 开单日期
     */
    @TableField("billingDate")
    private String billingDate;

    /**
     * 交货日期
     */
    @TableField("deliveryDate")
    private String deliveryDate;

    /**
     * 客户名称
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
    private String documentsNo;

    /**
     * 备注
     */
    private String remark;

    private Long id;


}
