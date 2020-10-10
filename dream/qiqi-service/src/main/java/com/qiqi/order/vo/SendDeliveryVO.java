package com.qiqi.order.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hc
 * @desc
 * @since 2020-10-10
 */
@Data
public class SendDeliveryVO {
    private String settlement;
    private Long orderId;
    private Integer sendNum;
    private String outNo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;
    private Long dId;
    private String name;
    private String boxType;

    private BigDecimal perPrice;
    private Boolean sign;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date shipDate;

    private BigDecimal beginReceive;

    private String audit;

    private String auditBy;

    private Long scheduleId;

    private String isProduct;

    private String wosState;

    private String stare;

    private String pressureSpecification;

    private Long customerId;

    private BigDecimal totalArea;

    private BigDecimal paperArea;

    private Boolean conventional;

    private String finished;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

    private Integer modCount;

    /**
     * 任务编号
     */
    private String no;


    /**
     * 客户单号
     */
    private String customerNo;

    /**
     * 款号
     */
    private String modelNo;


    /**
     * 材质
     */
    private String material;

    /**
     * 订单数量
     */
    private Integer orderNum;

    /**
     * 纸板到货数量
     */
    private Integer incomeNum;

    /**
     * 仓位
     */
    private String space;

    /**
     * 已产数量
     */
    private Integer productNum;

    /**
     * 成品仓位
     */
    private String productSpace;


    /**
     * 损耗数量
     */
    private Integer lossNum;

    /**
     * 下单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    /**
     * 交货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    /**
     * 常用箱
     */
    private String usedBox;

    /**
     * 单位
     */
    private String unit;

    /**
     * 长
     */
    private Integer length;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 高
     */
    private Integer height;

    /**
     * 组合
     */
    private String groups;


    /**
     * 金额
     */
    private BigDecimal money;

    private String signs;

    /**
     * 供方
     */
    private String supplier;

    /**
     * 纸长
     */
    private Integer paperLength;

    /**
     * 纸宽
     */
    private Integer paperWidth;

    /**
     * 制片数量
     */
    private Integer paperNum;

    /**
     * 无压线
     */
    @TableField("noLine_press")
    private Boolean nolinePress;

    /**
     * 存料
     */
    private String materialStock;

    /**
     * 耗用门幅
     */
    private Integer fabric;

    /**
     * 门幅倍数
     */
    private Integer ratio;

    /**
     * 打包
     */
    private String pack;

    /**
     * 打钉数
     */
    private Integer nailingNumber;

    /**
     * 钉类
     */
    private String nailClass;

    /**
     * 颜色
     */
    private String color;

    /**
     * 印刷版面
     */
    private String printSurface;

    /**
     * 分压
     */
    private Boolean partialPressure;

    /**
     * 裱糊
     */
    private Boolean pasting;

    /**
     * 印刷
     */
    private Boolean printing;

    /**
     * 开槽
     */
    private Boolean grooving;

    /**
     * 横切
     */
    @TableField("dieCutting")
    private Boolean dieCutting;

    /**
     * 结合
     */
    private String combine;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片
     */
    private String img;

    private String refundNum;

    private Date refundTime;
}
