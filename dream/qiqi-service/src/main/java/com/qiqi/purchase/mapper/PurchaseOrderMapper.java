package com.qiqi.purchase.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.purchase.entity.PurchaseOrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 采购单 Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrderDO> {

    /**
     * 连表查询
     * @param objectPage
     * @param str
     * @return
     */
    IPage SuppAndPurList(Page<Object> objectPage, String str);

    /**
     * 清空
     * @param id
     * @return
     */
    Boolean empty(Long id);
}
