package com.qiqi.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 颜色
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("colour")
public class ColourDO extends BaseModel {

    private static final long serialVersionUID=1L;

    private String name;


}
