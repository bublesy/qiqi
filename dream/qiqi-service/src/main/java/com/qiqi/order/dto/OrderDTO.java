package com.qiqi.order.dto;

import com.qiqi.order.entity.OrderDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-17
 */
@Data
public class OrderDTO extends OrderDO {
    private Long page;
    private Long count;

    private String myDate;
}
