package com.qiqi.purchase.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 采购单
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("purchase_order")
public class PurchaseOrderDO extends BaseModel {

    private static final long serialVersionUID=1L;


    private String returnNum;
    private String returnDate;
    private String position;
    private Date creditDate;
    private String endProductPos;

    private String settlementPeriod;

    private String discount;

    private String discountAmount;

    private String profit;

    private String inProductDate;

    private Long orderId;

    private String costPrice;


    /**
     * 供应商id
     */
    private String supplierId;

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


}
