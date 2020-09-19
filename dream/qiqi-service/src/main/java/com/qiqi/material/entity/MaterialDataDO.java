package com.qiqi.material.entity;

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
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("material_data")
public class MaterialDataDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 品名规格
     */
    private String specification;

    /**
     * 单位
     */
    private String unit;


}
