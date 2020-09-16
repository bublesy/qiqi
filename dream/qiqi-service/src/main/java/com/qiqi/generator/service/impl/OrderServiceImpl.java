package com.qiqi.generator.service.impl;

import com.qiqi.generator.entity.OrderDO;
import com.qiqi.generator.mapper.OrderMapper;
import com.qiqi.generator.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

}
