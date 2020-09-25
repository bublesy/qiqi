package com.qiqi.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
<<<<<<< HEAD
import com.qiqi.order.dto.BillsDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.vo.TotalVO;
import org.springframework.web.bind.annotation.RequestParam;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.qiqi.order.dto.OrderDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.vo.TotalVO;
>>>>>>> parent of 2b5953c... 合并 #215024 从 djh 到 master
=======
>>>>>>> hc
=======
>>>>>>> hc

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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD

    /**
     * 计算总价
     * @param startDate
     * @param endDate
     * @param customerId
     * @return
     */
    List<TotalVO> getTotal(String startDate, String endDate, Long customerId);


=======
    List<OrderDTO> getAllBill(Long page, Long count, Long customerId, Date deliveryDate);

    List<TotalVO> getTotal(Date deliveryDate);
>>>>>>> parent of 2b5953c... 合并 #215024 从 djh 到 master
=======

    List<TotalVO> getTotal(String startDate, String endDate, Long customerId);
>>>>>>> hc
=======

    List<TotalVO> getTotal(String startDate, String endDate, Long customerId);
>>>>>>> hc
}
