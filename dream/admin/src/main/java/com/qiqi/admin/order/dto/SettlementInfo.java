package com.qiqi.admin.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hc
 * @desc
 * @since 2020-09-27
 */
@Data
public class SettlementInfo {

    private Long orderId;

    /**
     * 过账状态
     */
    private String post;

    /**
     * 结算状态
     */
    private String settlement;

    private BigDecimal payed;

    private BigDecimal money;

    private BigDecimal beginReceive;
}
