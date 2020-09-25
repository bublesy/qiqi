package com.qiqi.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
<<<<<<< HEAD
import com.qiqi.order.dto.BillsDTO;
=======
import com.qiqi.order.dto.OrderDTO;
>>>>>>> parent of 2b5953c... 合并 #215024 从 djh 到 master
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.mapper.OrderMapper;
import com.qiqi.order.service.OrderService;
import com.qiqi.order.vo.TotalVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public IPage<OrderDO> GetList(Page<Object> objectPage, String customerName, String quantityOverdue, String time, String date) {
        return orderMapper.getList(objectPage,customerName,quantityOverdue,time,date);
    }

<<<<<<< HEAD
    @Override
<<<<<<< HEAD
    public List<TotalVO> getTotal(String startDate, String endDate, Long customerId) {
        return orderMapper.getTotal(startDate, endDate, customerId);
=======
    public List<OrderDTO> getAllBill(Long page, Long count, Long customerId, Date deliveryDate) {
        List<OrderDTO> allOrder = orderMapper.getAllOrder(page,count,customerId,deliveryDate);
        return allOrder;
    }

    @Override
    public List<TotalVO> getTotal(Date deliveryDate) {
        return orderMapper.getTotal(deliveryDate);
>>>>>>> parent of 2b5953c... 合并 #215024 从 djh 到 master
=======

    @Override
    public List<TotalVO> getTotal(String startDate, String endDate, Long customerId) {
        return orderMapper.getTotal(startDate,endDate,customerId);
>>>>>>> hc
    }
}
