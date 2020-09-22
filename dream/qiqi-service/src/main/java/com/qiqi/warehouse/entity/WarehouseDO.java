package com.qiqi.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 仓库
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("warehouse")
public class WarehouseDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 入仓单号
     */
    private String warehouseNo;

    private String deliveryDate;

    private String deliveryStatus;
    private String orderId;

    private String customerId;
    private String deliveryQuantity;

    private String specifications;
    private String taskNumber;

    private String  orderQuantity;
    /**
     * 单据类型
     */
    private String noType;

    /**
     * 开单人 id
     */
    private Long drawerId;

    /**
     * 开单时间
     */
    private String billingDate;

    private String checkNum;

    private String checkDate;
    /**
     * 过账
     */
    private String carryTo;

    /**
     * 过账日期
     */
    private String postingDate;

    /**
     * 入仓日期
     */
    private String warehousingDate;

    /**
     * 材质
     */
    private String material;

    /**
     * 纸长
     */
    private String paperLength;

    /**
     * 纸宽
     */
    private String paperWidth;

    /**
     * 单价
     */
    private String unitPrice;

    /**
     * 购入数量
     */
    private String purchaseQuantity;

    /**
     * 仓位
     */
    private String position;

    /**
     * 备注
     */
    private String remark;


}
