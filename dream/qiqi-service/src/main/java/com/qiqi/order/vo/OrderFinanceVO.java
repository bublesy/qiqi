package com.qiqi.order.vo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderFinanceVO {
    private Long customerId;
    private String name;
    private String date;
    private BigDecimal money;
    private BigDecimal begin;
}