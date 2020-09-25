package com.qiqi.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.dto.BillsDTO;
import com.qiqi.order.dto.OrderFinanceDTO;
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


//    List<TotalVO> getTotal(String startDate, String endDate, Long customerId);

    /**
     *
     * @param objectPage
     * @param time
     * @param date
     * @return
     */
    PageEntity<OrderFinanceDTO> listOrder(Page objectPage, Long customerId, String time, String date);
}
