package com.qiqi.material.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
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
@TableName("material_picking")
public class MaterialPickingDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 日期
     */
    private Date date;

    /**
     * 开单员
     */
    private String partOrder;

    /**
     * 备注
     */
    private String note;

    /**
     * 领料人
     */
    private String pickingPeople;


}
