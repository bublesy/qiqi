package com.qiqi.admin.order.dto;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiqi.basicdata.entity.CustomerInformationDO;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.entity.OrderDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hc
 * @desc
 * @since 2020-09-22
 */
@Data
public class BillVO {

    private Long id;

    private Long customerId;

    private String name;
    private String outNo;

    private String no;

    private JSONArray settlementDate;

    @JsonFormat(pattern = "yyyy-MM")
    private Date shipDate;

    @JsonFormat(pattern = "yyyy-MM")
    private Date deliveryDate;

    private BigDecimal beginReceive;

    private String modelNo;

    private String boxType;

    private Integer length;

    private Integer width;

    private Integer height;

    private Integer orderNum;

    private BigDecimal perPrice;

    private BigDecimal money;

    private String post;

    private String settlement;

    private BigDecimal payed;

}
