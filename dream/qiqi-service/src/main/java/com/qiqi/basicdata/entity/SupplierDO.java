package com.qiqi.basicdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 供应商
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplier")
public class SupplierDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 编码
     */
    private String code;

    /**
     * 简称
     */
    private String abbreviation;

    /**
     * 全称
     */
    private String fullName;

    /**
     * 电话
     */
    private String telephone;

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
    private String contacts;

    /**
     * 网址
     */
    private String website;

    /**
     * 地址
     */
    private String address;


}
