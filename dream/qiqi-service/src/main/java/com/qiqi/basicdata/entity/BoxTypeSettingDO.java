package com.qiqi.basicdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 箱型设定
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("box_type_setting")
public class BoxTypeSettingDO extends BaseModel {

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
     * 限定纸长
     */
    private Boolean limitPaperLength;


}
