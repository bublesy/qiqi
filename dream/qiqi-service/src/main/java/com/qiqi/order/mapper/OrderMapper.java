package com.qiqi.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
<<<<<<< HEAD
import com.qiqi.order.dto.BillsDTO;
=======
import com.qiqi.order.dto.OrderDTO;
>>>>>>> parent of 2b5953c... 合并 #215024 从 djh 到 master
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.vo.TotalVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
    /**
     * aa
     * @param objectPage
     * @param customerName
     * @param quantityOverdue
     * @param time
     * @param date
     * @return
     */
    IPage<OrderDO> getList(@Param("objectPage") Page<Object> objectPage,@Param("customerName")  String customerName,
                           @Param("quantityOverdue")  String quantityOverdue,@Param("time")  String time,
                           @Param("date") String date);

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD

    List<TotalVO> getTotal(String startDate, String endDate, Long customerId);
=======
    List<OrderDTO> getAllOrder(Long page, Long count, Long customerId, Date deliveryDate);

    List<TotalVO> getTotal(Date deliveryDate);
>>>>>>> parent of 2b5953c... 合并 #215024 从 djh 到 master
=======

    List<TotalVO> getTotal(String startDate, String endDate, Long customerId);
>>>>>>> hc
=======

    List<TotalVO> getTotal(String startDate, String endDate, Long customerId);
>>>>>>> hc
}
