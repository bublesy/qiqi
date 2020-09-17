package com.qiqi.purchase.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import java.time.LocalDateTime;
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

    /**
     * 供应商id
     */
    private String supplierId;

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
    private String customerId;

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

    /**
     * 备注
     */
    private String remark;


}
