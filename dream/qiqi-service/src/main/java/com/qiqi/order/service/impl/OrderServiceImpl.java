package com.qiqi.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.mapper.OrderMapper;
import com.qiqi.order.service.OrderService;
import org.springframework.stereotype.Service;

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

}
