package com.qiqi.staff.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 职位
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("position")
public class PositionDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 职位名称
     */
    private String name;


}
