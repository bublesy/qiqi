package com.qiqi.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 送货单
 * </p>
 *
 * @author QiQiDream
 * @since 2020-10-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("delivery_note")
public class DeliveryNoteDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 送货数量
     */
    private Integer sendNum;

    /**
     * 送货单号
     */
    private String outNo;


}
