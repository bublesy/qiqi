package com.qiqi.basicdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 客户报价单
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("customer_quotation")
public class CustomerQuotationDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 编码
     */
    private String code;

    /**
     * 简称
     */
    private String shorts;

    /**
     * 全称
     */
    private String fullName;

    /**
     * 销售平方价
     */
    private BigDecimal squaredPrice;

    /**
     * 箱价
     */
    private BigDecimal BoxPrice;


}
