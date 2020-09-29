package com.qiqi.vendor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("vendor")
public class VendorDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 厂商
     */
    private String name;


}
