package com.qiqi.warehouse.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class WarehouseDTO {
    /**
     * 供应商id
     */
    private String supplierId;


    private String deliveryQuantity;
    private Long page;
    private Long size;
    private String typeNo;
    private String signBackState;
    private String outNo;
    private String deliveryman;
    private String posting;
    private String note;

    private Long eId;

    private Date shipDate;

    private String audit;

    private String auditBy;

    private Boolean sign;

    private String carryTo;
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
     * 客户名称
     */
    private String name;

    /**
     * 客户单号
     */
    private String customerNo;

    /**
     * 款号
     */
    private String modelNo;

    /**
     * 箱型
     */
    private String boxType;

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
     * 已送数量
     */
    private Integer sendNum;

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
     * 单价
     */
    private BigDecimal perPrice;

    /**
     * 金额
     */
    private BigDecimal money;

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

    private String taskNumber;

    private String checkNum;

    private String outDate;

    private Integer alreadyDeliveryQuantity;
    private String checkDate;


    private String deliveryStatus;

    private String createdBy;


    /**
     * 计价方式
     */
    @TableField("pricingMethod")
    private String pricingMethod;

    private String warehouseNo;

    private String noType;

    /**
     * 入仓日期
     */
    private String warehousingDate;
    /**
     * 开单日期
     */
    @TableField("billingDate")
    private String billingDate;


    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 楞型
     */
    @TableField("ridgeType")
    private String ridgeType;


    /**
     * 分压规格
     */
    @TableField("parPreSpe")
    private String parPreSpe;



    /**
     * 订单数量
     */
    @TableField("orderQuantity")
    private String orderQuantity;

    /**
     * 采购数量
     */
    @TableField("purchaseQuantity")
    private String purchaseQuantity;

    /**
     * 配料面积
     */
    private String batching;

    /**
     * 平方价
     */
    @TableField("squarePrice")
    private String squarePrice;

    /**
     * 单价
     */
    @TableField("unitPrice")
    private String unitPrice;

    /**
     * 金额
     */
    private String amount;

    private String position;


    private String documentsNo;


    private Long id;
}
