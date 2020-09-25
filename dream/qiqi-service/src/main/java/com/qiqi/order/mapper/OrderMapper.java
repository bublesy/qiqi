package com.qiqi.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.order.dto.BillsDTO;
import com.qiqi.order.dto.OrderFinanceDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.vo.OrderFinanceVO;
import com.qiqi.order.vo.TotalVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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


    List<TotalVO> getTotal(String startDate, String endDate, Long customerId);

    /**
     *
     * @param time
     * @param date
     * @return
     */
    List<OrderFinanceVO> listOrder(@Param("ids") List<Long> ids, @Param("time") String time, @Param("date") String date);

    IPage<Long> getPage(@Param("objectPage") Page objectPage, @Param("time") String time, @Param("date") String date);

    List<OrderFinanceVO> listTotalOrder(@Param("ids") List<Long> ids, @Param("time") String time, @Param("date") String date);

}
