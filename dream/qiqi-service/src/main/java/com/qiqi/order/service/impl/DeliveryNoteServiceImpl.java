package com.qiqi.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.order.entity.DeliveryNoteDO;
import com.qiqi.order.mapper.DeliveryNoteMapper;
import com.qiqi.order.service.DeliveryNoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiqi.order.vo.DeliveryVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 送货单 服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-10-08
 */
@Service
public class DeliveryNoteServiceImpl extends ServiceImpl<DeliveryNoteMapper, DeliveryNoteDO> implements DeliveryNoteService {
    @Resource
    DeliveryNoteMapper deliveryNoteMapper;
    @Override
    public IPage<DeliveryVO> getList(Page<Object> objectPage, String signs, String name) {
        return deliveryNoteMapper.getList(objectPage,signs,name);
    }
}
