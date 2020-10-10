package com.qiqi.order.vo;

import com.qiqi.finance.entity.CustomerDetailDO;
import com.qiqi.order.entity.OrderDO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hc
 * @desc
 * @since 2020-10-09
 */
@Data
public class WarningVO extends OrderDO {
    private String settlement;

    private BigDecimal payed;

}
