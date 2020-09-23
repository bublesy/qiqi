package com.qiqi.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.order.entity.OrderDO;
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

    List<OrderDO> getAllOrder();
}
