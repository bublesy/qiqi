package com.qiqi.admin.order.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.admin.order.util.TimeAddEight;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.dto.OrderDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hc
 * @since 2020-09-17
 */
@Api("出货日报表接口")
@RestController
@RequestMapping("/ship")
public class ShipDailyController {

    @Resource
    private OrderService orderService;

    @PostMapping("/list")
    public PageEntity<OrderDO> getList(@RequestBody OrderDTO query){
        QueryWrapper<OrderDO> queryWrapper = new QueryWrapper<>();
        if(query.getDeliveryDate() != null){
            query.setDeliveryDate(TimeAddEight.formatTimeEight(query.getDeliveryDate()));
        }
        queryWrapper
                .eq(!ObjectUtils.isEmpty(query.getDeliveryDate()),"delivery_date",query.getDeliveryDate())
                .like(StringUtils.isNotBlank(query.getName()),"name",query.getName())
                .like(StringUtils.isNotBlank(query.getNo()),"no",query.getNo());
        Page<OrderDO> iPage = orderService.page(new Page<OrderDO>(query.getPage(), query.getCount()), queryWrapper);
        return new PageEntity<>(iPage.getTotal(), Convert.convert(new TypeReference<List<OrderDO>>() {}, iPage.getRecords()));

    }
}
