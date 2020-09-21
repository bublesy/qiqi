package com.qiqi.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.mapper.OrderMapper;
import com.qiqi.order.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Override
    public IPage<OrderDO> GetList(Page<Object> objectPage, String customerName, String quantityOverdue, String time, Date date) {
        return orderMapper.getList(objectPage,customerName,quantityOverdue,time,date);
    }
}
