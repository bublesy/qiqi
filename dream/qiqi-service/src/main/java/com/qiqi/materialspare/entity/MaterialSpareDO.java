package com.qiqi.materialspare.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 材料备用库存
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("material_spare")
public class MaterialSpareDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 供应商id
     */
    private String supplierId;

    /**
     * 材料
     */
    private String materialId;

    private String ridgeTypeId;


    /**
     * 规格
     */
    private String specification;

    /**
     * 纸长
     */
    private String paperLength;

    /**
     * 纸宽
     */
    private String paperWidth;

    /**
     * 购入数量
     */
    private String purchaseQuantity;

    /**
     * 单价
     */
    private String unitPrice;

    /**
     * 备注
     */
    private String remark;


}
