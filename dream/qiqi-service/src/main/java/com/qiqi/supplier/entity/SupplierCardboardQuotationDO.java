package com.qiqi.supplier.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 供应商纸板报价
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplier_cardboard_quotation")
public class SupplierCardboardQuotationDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 供应商id
     */
    private String supplierId;

    /**
     * 报价单位
     */
    private String quotationUnit;
    /**
     * 纸板报价
     */
    private String cardboardQuotation;

    /**
     * 优惠设定
     */
    private String preferentialSetting;


}
