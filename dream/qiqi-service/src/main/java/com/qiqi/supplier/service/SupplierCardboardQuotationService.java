package com.qiqi.supplier.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.supplier.entity.SupplierCardboardQuotationDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 供应商纸板报价 服务类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
public interface SupplierCardboardQuotationService extends IService<SupplierCardboardQuotationDO> {

    /**
     * 列表查询
     * @param code
     * @param abbreviation
     * @param time
     * @param objectPage
     * @return {@link IPage}
     * @author sun
     * @time 2020/9/15 14:40
     */
    IPage findList(String code, String abbreviation, String time, Page<Object> objectPage);
}
