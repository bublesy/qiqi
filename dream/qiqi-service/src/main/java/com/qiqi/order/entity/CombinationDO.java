package com.qiqi.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 组合
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("combination")
public class CombinationDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 名称
     */
    private String name;


}
