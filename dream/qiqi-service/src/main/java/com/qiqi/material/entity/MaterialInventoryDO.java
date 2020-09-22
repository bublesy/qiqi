package com.qiqi.material.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 辅料库存
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("material_inventory")
public class MaterialInventoryDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 规格
     */
    private String specificationId;

    private String unit;

    private String supplierId;

    private String partOrder;

    private String perPrice;
    private String money;

    /**
     * 数量
     */
    private Integer number;


}
