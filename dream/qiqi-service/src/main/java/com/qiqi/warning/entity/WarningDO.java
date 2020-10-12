package com.qiqi.warning.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 预警表
 * </p>
 *
 * @author QiQiDream
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("warning")
public class WarningDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 预警订单天数
     */
    private Integer orderDay;

    /**
     * 预警仓库库存数量
     */
    private Integer wareNum;

    /**
     * 预警成品仓库库存数量
     */
    private Integer endWareNum;

    /**
     * 预警供应商结算日期
     */
    private Integer supplierDay;

    /**
     * 预警成品仓库发货数量
     */
    private Integer endWareSendNum;


}
