package com.qiqi.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 曾燚
 * @ClassName OrderFinanceDTO
 * @Description TODO
 * @Date 2020/9/25,0025
 * @Version 1.0
 */
@Data
public class OrderFinanceDTO {
    private Long id;
    private Long customerId;
    private String name;
    private Map<String, BigDecimal> dates;
    private BigDecimal total;
    private List<OrderFinanceDTO> children;
}
