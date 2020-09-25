package com.qiqi.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hc
 * @desc
 * @since 2020-09-24
 */
@Data
public class BillsDTO {

    private Long id;

    private String name;

    private BigDecimal money;

    private Date deliveryDate;

    private BigDecimal beginReceive;

    private Long customerId;

    private String groudBy;
}
