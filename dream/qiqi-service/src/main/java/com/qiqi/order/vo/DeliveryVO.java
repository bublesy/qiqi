package com.qiqi.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hc
 * @desc
 * @since 2020-10-08
 */
@Data
public class DeliveryVO {

    private Long id;
    private Long orderId;
    private Integer sendNum;
    private String outNo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;
    private String name;
    private String boxType;
    private BigDecimal perPrice;
    private Boolean sign;
}
