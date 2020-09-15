package com.qiqi.supplier.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.supplier.entity.SupplierCardboardQuotationDO;
import com.qiqi.supplier.mapper.SupplierCardboardQuotationMapper;
import com.qiqi.supplier.service.SupplierCardboardQuotationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 供应商纸板报价 服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Service
public class SupplierCardboardQuotationServiceImpl extends ServiceImpl<SupplierCardboardQuotationMapper, SupplierCardboardQuotationDO> implements SupplierCardboardQuotationService {
    @Resource
    private SupplierCardboardQuotationMapper supplierCardboardQuotationMapper;
    @Override
    public IPage findList(String code, String abbreviation, String time, Page<Object> objectPage) {
        return supplierCardboardQuotationMapper.findList(code,abbreviation,time,objectPage);
    }
}
