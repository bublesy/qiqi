package com.qiqi.cardboardinvenroty.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 纸板库存
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cardboard_inventory")
public class CardboardInventoryDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 材质
     */
    private String material;

    /**
     * 纸长
     */
    private String paperLength;

    /**
     * 纸宽
     */
    private String paperWidth;

    /**
     * 库存
     */
    private String stock;


}
