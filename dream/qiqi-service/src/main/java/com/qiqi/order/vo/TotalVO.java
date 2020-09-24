package com.qiqi.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hc
 * @desc
 * @since 2020-09-23
 */
@Data
public class TotalVO {

    private Date myDate;

    private BigDecimal money;
}
