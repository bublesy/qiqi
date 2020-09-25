package com.qiqi.admin.warehouse.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class WarehouseVO {

    /**
     * 供应商id
     */
    private String supplierId;

    private String taskNumber;

    private String checkNum;

    private String outNo;
    private String outDate;

    private String checkDate;

    private String deliveryQuantity;

    private String deliveryStatus;

    private String createdBy;
    private String customerId;


    /**
     * 计价方式
     */
    @TableField("pricingMethod")
    private String pricingMethod;

    private String warehouseNo;

    private String noType;

    /**
     * 过账
     */
    private String carryTo;

    /**
     * 入仓日期
     */
    private String warehousingDate;
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

    private String position;

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
