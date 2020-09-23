package com.qiqi.admin.order.dto;

import com.qiqi.basicdata.entity.CustomerInformationDO;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.entity.OrderDO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hc
 * @desc
 * @since 2020-09-22
 */
@Data
public class BillVO {
    PageEntity<OrderDO> orderDOPageEntity;

    /**
     * 全称
     */
    private String fullName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 地址
     */
    private String address;
}
