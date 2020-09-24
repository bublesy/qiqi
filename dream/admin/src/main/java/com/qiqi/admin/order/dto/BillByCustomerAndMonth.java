package com.qiqi.admin.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hc
 * @desc
 * @since 2020-09-24
 */
@Data
public class BillByCustomerAndMonth {
    private String name;
    private List<BigDecimal> money;
}
