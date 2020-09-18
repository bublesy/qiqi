package com.qiqi.basicdata.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.basicdata.entity.SupplierCardboardQuotationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 供应商纸板报价 Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
public interface SupplierCardboardQuotationMapper extends BaseMapper<SupplierCardboardQuotationDO> {

    /**
     * 列表查询
     * @param code
     * @param abbreviation
     * @param time
     * @param objectPage
     * @return {@link IPage}
     * @author sun
     * @time 2020/9/15 14:42
     */
    IPage findList(String code, String abbreviation, String time, Page<Object> objectPage);
}
