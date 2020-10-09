package com.qiqi.admin.order.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiqi.order.entity.DeliveryNoteDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import com.qiqi.order.vo.DeliveryVO;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.order.service.DeliveryNoteService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 送货单 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-10-08
 */
@Api("送货单相关接口")
@RestController
@RequestMapping("/delivery/note")
public class DeliveryNoteController {

    @Resource
    private DeliveryNoteService deliveryNoteService;
    @Resource
    private OrderService orderService;

    @ApiOperation("生成出货日报表")
    @GetMapping("")
    public PageEntity<DeliveryVO> delivery(@RequestParam(defaultValue = "1") Long page,
                                           @RequestParam(defaultValue = "10") Long count,
                                           @RequestParam(required = false) String shipDate,
                                           @RequestParam String name,
                                           @RequestParam String outNo){
        IPage<DeliveryNoteDO> iPage = deliveryNoteService.page(new Page<>(page,count),new QueryWrapper<DeliveryNoteDO>()
                .apply(StringUtils.isNotBlank(shipDate),
                        "date_format (created_time,'%Y-%m-%d') = '"+shipDate+"'" )
                .eq(StringUtils.isNotBlank(outNo),"out_no",outNo));
        if(iPage.getRecords().size()==0){
            return null;
        }
        List<Long> orderIds = iPage.getRecords().stream().map(DeliveryNoteDO::getOrderId).collect(Collectors.toList());
        if (orderIds.size() <= 0){
            return null;
        }
        List<OrderDO> orders = orderService.listByIds(orderIds);
        if(orders==null || orders.size() <= 0){
            return null;
        }
        if(name != null && name != ""){
            orders = orders.stream().filter(orderDO -> orderDO.getName().equals(name)).collect(Collectors.toList());
        }
        List<DeliveryVO> convert = Convert.convert(new TypeReference<List<DeliveryVO>>() {}, orders);
        convert.forEach(data->{
            iPage.getRecords().forEach(data2->{
                if(data.getId().equals(data2.getOrderId())){
                    data.setShipDate(data2.getCreatedTime());
                    data.setOutNo(data2.getOutNo());
                    data.setSendNum(data2.getSendNum());
                }
            });
        });
        return new PageEntity<>(iPage.getTotal(),convert);
    }

}
