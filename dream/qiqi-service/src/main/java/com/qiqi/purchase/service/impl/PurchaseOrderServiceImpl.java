package com.qiqi.purchase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.purchase.entity.PurchaseOrderDO;
import com.qiqi.purchase.mapper.PurchaseOrderMapper;
import com.qiqi.purchase.service.PurchaseOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 采购单 服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Service
public class PurchaseOrderServiceImpl extends ServiceImpl<PurchaseOrderMapper, PurchaseOrderDO> implements PurchaseOrderService {
    @Resource
    private PurchaseOrderMapper purchaseOrderMapper;
    @Override
    public IPage SuppAndPurList(Page<Object> objectPage, List<Long> ids) {
        String join = StringUtils.join(ids,",");
        return purchaseOrderMapper.SuppAndPurList(objectPage,join);
    }

    @Override
    public Boolean empty(Long id) {
        return purchaseOrderMapper.empty(id);
    }
}
