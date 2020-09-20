package com.qiqi.material.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("material_in_store")
public class MaterialInStoreDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 日期
     */
    private String date;

    private String unitId;

    private String specificationId;

    /**
     * 供方
     */
    private String supplier;

    /**
     * 开单员
     */
    private String partOrder;

    /**
     * 原始单号
     */
    private String no;

    /**
     * 备注
     */
    private String note;

    /**
     * 单价
     */
    private String perPrice;

    /**
     * 金额
     */
    private String money;

    /**
     * 数量
     */
    private Integer num;


}
