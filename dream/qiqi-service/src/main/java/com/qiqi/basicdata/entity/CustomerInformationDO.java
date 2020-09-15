package com.qiqi.basicdata.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 客户资料
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("customer_information")
public class CustomerInformationDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 编码
     */
    private String code;

    /**
     * 简称
     */
    private String shorts;

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
     * 联系人
     */
    private String contact;

    /**
     * 网址
     */
    private String url;

    /**
     * 地址
     */
    private String address;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 指定业务员
     */
    private String salesman;

    /**
     * 银行账号
     */
    private String account;

    /**
     * 税务登记号
     */
    private String registration;

    /**
     * 结算方式
     */
    private String payment;

    /**
     * 币别
     */
    private String currency;

    /**
     * 期初应收
     */
    @TableField("beginReceive")
    private BigDecimal beginReceive;

    /**
     * 信用额度
     */
    private BigDecimal credit;


}
