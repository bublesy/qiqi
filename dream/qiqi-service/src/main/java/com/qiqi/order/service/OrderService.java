package com.qiqi.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiqi.order.dto.BillsDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.vo.TotalVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
public interface OrderService extends IService<OrderDO> {

    /**
     * 列表
     * @param objectPage
     * @param customerName
     * @param quantityOverdue
     * @param time
     * @param date
     * @return
     */
    IPage<OrderDO> GetList(Page<Object> objectPage, String customerName, String quantityOverdue, String time, String date);


    List<TotalVO> getTotal(Date startDate, Date endDate, Long customerId);
}
