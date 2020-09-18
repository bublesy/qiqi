package com.qiqi.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order")
public class OrderDO extends BaseModel {

    private static final long serialVersionUID=1L;

    private String no;

    private String name;

    private String customerNo;

    private String modelNo;

    private String boxType;

    private String material;

    private String cartonSize;

    private Integer orderNum;

    private Integer incomeNum;

    private String space;

    private Integer productNum;

    private String productSpace;

    private Integer sendNum;

    private Integer lossNum;

    private Date orderDate;

    private Date deliveryDate;

    private String usedBox;


}
