package com.qiqi.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.dto.OrderFinanceDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.mapper.OrderMapper;
import com.qiqi.order.service.OrderService;
import com.qiqi.order.vo.OrderFinanceVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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


//    @Override
//    public List<TotalVO> getTotal(Date startDate, Date endDate, Long customerId) {
//        return orderMapper.getTotal(startDate,endDate,customerId);
//    }

    /**
     * @param objectPage
     * @param time
     * @param date
     * @return
     */
    @Override
    public PageEntity<OrderFinanceDTO> listOrder(Page objectPage, Long customerId, String time, String date) {
        List<Long> ids=new ArrayList<>();
        Long totalPage=1L;
        List<OrderFinanceDTO> dtos = new ArrayList<>();

        if (ObjectUtils.isEmpty(customerId)){
            IPage<Long> page = baseMapper.getPage(objectPage, time, date);
            ids=page.getRecords();
            totalPage=page.getTotal();
        }else {
            ids.add(customerId);
        }
        if (totalPage>0) {
            List<OrderFinanceVO> iPage = baseMapper.listOrder(ids, time, date);
            Map<Long, List<OrderFinanceVO>> collect = iPage.stream().collect(Collectors.groupingBy(OrderFinanceVO::getCustomerId));
            for (Long key : collect.keySet()) {
                Map<String, BigDecimal> dates = new HashMap<>(16);
                Map<String, BigDecimal> beginDates = new HashMap<>(16);
                Map<String, BigDecimal> endDates = new HashMap<>(16);

                OrderFinanceDTO dto = new OrderFinanceDTO();
                dto.setId(key);
                OrderFinanceDTO begin = new OrderFinanceDTO();
                begin.setId(key+2222);
                OrderFinanceDTO endTotal = new OrderFinanceDTO();
                endTotal.setId(key+1234);

                dto.setCustomerId(key);
                dto.setName(collect.get(key).get(0).getName());
                final BigDecimal[] decimal = {new BigDecimal("0.00")};
                collect.get(key).forEach(a -> {
                    dates.put(a.getDate(), a.getMoney());
                    beginDates.put(a.getDate(), a.getBegin());
                    if(a.getBegin() != null){
                        endDates.put(a.getDate(), a.getMoney().subtract(a.getBegin()));
                    }
                    decimal[0] = decimal[0].add(a.getMoney());
                });
                dto.setDates(dates);
                dto.setTotal(decimal[0]);
                begin.setDates(beginDates);
                endTotal.setDates(endDates);
                List<OrderFinanceDTO> children = new ArrayList<>();
                children.add(0,begin);
                children.add(endTotal);
                dto.setChildren(children);
                dtos.add(dto);
            }
            List<OrderFinanceVO> totalOrder = baseMapper.listTotalOrder(ids, time, date);
            Map<String, BigDecimal> totalDates = new HashMap<>(16);
            totalOrder.forEach(a->{
                totalDates.put(a.getDate(),a.getMoney());
            });
            OrderFinanceDTO totalDto = new OrderFinanceDTO();
            totalDto.setName("总合计");
            totalDto.setDates(totalDates);
            dtos.add(totalDto);
        }
        return new PageEntity<>(totalPage,dtos);
    }
}
