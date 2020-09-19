package com.qiqi.endproductwarehouse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品仓库
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("end_product_warehouse")
public class EndProductWarehouseDO extends BaseModel {

    private static final long serialVersionUID=1L;

    private String orderId;
    /**
     * 供应商id
     */
    private String supplierId;

    private String carryTo;

    private String unitPrice;
    private String taskNumber;
    /**
     * 入仓单号
     */
    private String warehouseNo;

    /**
     * 开单人
     */
    private Long drawer;

    /**
     * 开单日期
     */
    private String billingData;

    /**
     * 客户id
     */
    private String customerId;

    /**
     * 交货日期
     */
    private String deliveryDate;

    /**
     * 客户单号
     */
    private String customerNo;

    /**
     * 箱型
     */
    private String boxType;

    /**
     * 规格
     */
    private String specifications;

    /**
     * 供应商id
     */
    private String typeNo;

    /**
     * 材质
     */
    private String material;

    /**
     * 订单数量
     */
    private String orderQuantity;

    /**
     * 送货数量
     */
    private String deliveryQuantity;

    /**
     * 长
     */
    private String length;

    /**
     * 宽
     */
    private String width;

    /**
     * 高
     */
    private String height;

    /**
     * 成品仓位
     */
    private String endProductPos;

    /**
     * 入仓时间
     */
    private String warehousingData;

    /**
     * 备注
     */
    private String remark;


}
