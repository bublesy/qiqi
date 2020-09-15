package com.qiqi.supplier.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 纸板资料设定
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("paperboard_data_setting")
public class PaperboardDataSettingDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 楞型
     */
    @TableField("ridgeType")
    private String ridgeType;

    /**
     * 描述
     */
    private String represent;


}
