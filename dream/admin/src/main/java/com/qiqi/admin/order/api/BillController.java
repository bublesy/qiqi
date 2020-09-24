package com.qiqi.admin.order.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.admin.order.dto.BillDTO;
import com.qiqi.admin.order.dto.BillVO;
import com.qiqi.basicdata.entity.CustomerInformationDO;
import com.qiqi.basicdata.service.CustomerInformationService;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.dto.OrderDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hc
 * @desc
 * @since 2020-09-22
 */
@Api(tags = "对账单")
@RestController
@RequestMapping("bill")
public class BillController {

    @Resource
    private OrderService orderService;

    @Resource
    private CustomerInformationService customerInformationService;

    @ApiOperation(value = "月结对账单")
    @PostMapping("")
    public BillVO getBill(@RequestBody BillDTO query){
        QueryWrapper<OrderDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!ObjectUtils.isEmpty(query.getDeliveryDate()),"delivery_date",query.getDeliveryDate())
                .eq(!ObjectUtils.isEmpty(query.getCustomerId()),"customer_id",query.getCustomerId());
        IPage<OrderDO> iPage = orderService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);

        PageEntity<OrderDO> orderDOPageEntity = new PageEntity<>(iPage.getTotal(), Convert.convert(new TypeReference<List<OrderDO>>() {
        }, iPage.getRecords()));
        BillVO billVO = new BillVO();
        if(query.getCustomerId() != null){
            CustomerInformationDO customerInformationDO = customerInformationService.getById(query.getCustomerId());
            if(customerInformationDO != null){
                BeanUtils.copyProperties(customerInformationDO,billVO);
            }
        }
        billVO.setOrderDOPageEntity(orderDOPageEntity);
        return billVO;
    }

    @ApiOperation(value = "应收款列表")
    @GetMapping("")
    public Map<Long, List<OrderDO>> getAllBill(@RequestParam(value = "page",defaultValue = "1") Long page,
                                               @RequestParam(value = "count",defaultValue = "10") Long count,
                                               @RequestParam(required = false) Long customerId,
                                               @RequestParam(required = false) Date deliveryDate){
//        List<OrderDO> list = orderService.list(new QueryWrapper<OrderDO>()
//        .eq(!ObjectUtils.isEmpty(deliveryDate),"delivery_date",deliveryDate)
//        .like(StringUtils.isNotBlank(name),"name",name));
//        Map<Long, List<OrderDO>> collect = list.stream().collect(Collectors.groupingBy(orderDO -> orderDO.getCustomerId()));
//        Collection<List<OrderDO>> values = collect.values();
//        int sum = list.stream().map(orderDO -> orderDO.getMoney())..sum();
        List<OrderDO> allBill = orderService.getAllBill(page,count,customerId,deliveryDate);
        Map<Long, List<OrderDO>> collect = allBill.stream().collect(Collectors.groupingBy(orderDO -> orderDO.getCustomerId()));
        return collect;
    }

    @ApiOperation(value = "总计")
    @GetMapping("/total")
    public List<OrderDO> getTotal(@RequestParam Date deliveryDate){
        return orderService.getTotal(deliveryDate);
    }
}
