package com.qiqi.finance.entity;

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
 * 
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(autoResultMap = true, value = "customer_detail")
public class CustomerDetailDO extends BaseModel {

    private static final long serialVersionUID=1L;

    private Long orderId;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONArray settlementDate;

    /**
     * 过账状态
     */
    private String post;

    /**
     * 结算状态
     */
    private String settlement;

    private BigDecimal payed;



}
