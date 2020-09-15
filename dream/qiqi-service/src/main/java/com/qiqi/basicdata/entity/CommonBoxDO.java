package com.qiqi.basicdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 常用箱资料管理

 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("common_box")
public class CommonBoxDO extends BaseModel {

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
     * 常用箱管理
     */
    private String commonBoxManagement;


}
