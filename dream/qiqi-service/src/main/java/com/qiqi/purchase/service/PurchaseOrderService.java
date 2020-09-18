package com.qiqi.purchase.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.purchase.entity.PurchaseOrderDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 采购单 服务类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
public interface PurchaseOrderService extends IService<PurchaseOrderDO> {

    /**
     * 查询
     * @param objectPage
     * @param ids
     * @return
     */
    IPage SuppAndPurList(Page<Object> objectPage, List<Long> ids);
}
