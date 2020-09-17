package com.qiqi.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 排期表
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("schedule")
public class ScheduleDO extends BaseModel {

    private static final long serialVersionUID=1L;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

    private Integer productDay;

    /**
     * 任务编号
     */
    private String no;

    /**
     * 客户简称
     */
    private String name;

    /**
     * 箱型
     */
    private String boxType;

    /**
     * 材质
     */
    private String material;

    /**
     * 楞型
     */
    private String stare;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 高
     */
    private Integer height;

    /**
     * 长
     */
    private Integer length;

    /**
     * 纸长
     */
    private Integer paperLength;

    /**
     * 纸宽
     */
    private Integer paperWidth;

    /**
     * 分压规格
     */
    private String pressureSpecification;

    /**
     * 单位
     */
    private String unit;

    /**
     * 订单数量
     */
    private Integer orderNum;

    /**
     * 成品数量
     */
    private Integer productNum;

    /**
     * 是否排期
     */
    private Boolean isSchedule;

    /**
     * 交货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date deliveryDate;


}
