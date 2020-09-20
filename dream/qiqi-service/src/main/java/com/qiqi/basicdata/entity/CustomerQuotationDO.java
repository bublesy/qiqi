package com.qiqi.basicdata.entity;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
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
@TableName(autoResultMap = true, value = "customer_quotation")
public class CustomerQuotationDO extends BaseModel {

    private static final long serialVersionUID=1L;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONArray boxType;

    private BigDecimal totalPrice;

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

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONArray paperName;


}
