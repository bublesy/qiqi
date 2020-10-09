package com.qiqi.admin.order.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.admin.order.dto.BillDTO;
import com.qiqi.admin.order.dto.BillVO;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.finance.entity.CustomerDetailDO;
import com.qiqi.finance.service.CustomerDetailService;
import com.qiqi.order.dto.OrderFinanceDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
    private CustomerDetailService customerDetailService;

    @ApiOperation(value = "月结对账单")
    @PostMapping("")
    public PageEntity<BillVO> getBill(@RequestBody BillDTO query){
        QueryWrapper<OrderDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()),"name",query.getName())
                    .apply(StringUtils.isNotBlank(query.getDeliveryDate()),
                            "date_format (delivery_date,'%Y-%m') = '"+query.getDeliveryDate()+"'" );
        Page<OrderDO> page = orderService.page(new Page<>(query.getPage(), query.getCount()),queryWrapper);
        List<OrderDO> orderList = page.getRecords();
        List<Long> orderIds = orderList.stream().map(data -> data.getId()).collect(Collectors.toList());
        List<CustomerDetailDO> customerDetails = customerDetailService.list(new QueryWrapper<CustomerDetailDO>()
                .in(orderIds != null && orderIds.size() > 0, "order_id", orderIds));
        List<BillVO> convert = Convert.convert(new TypeReference<List<BillVO>>() {}, orderList);
        convert.forEach(data->{
            customerDetails.forEach(data2->{
                if(data.getId().equals(data2.getOrderId())){
                    data.setPost(data2.getPost());
                    data.setSettlement(data2.getSettlement());
                    data.setPayed(data2.getPayed());
                    data.setSettlementDate(data2.getSettlementDate());
                }
            });
        });
        return new PageEntity<>(page.getTotal(),convert);
    }

    @ApiOperation(value = "应收款列表")
    @GetMapping("")
    public PageEntity<OrderFinanceDTO> getAllBill(@RequestParam(value = "page",defaultValue = "1") Long page,
                                          @RequestParam(value = "count",defaultValue = "10") Long count,
                                          @RequestParam(required = false) Long customerId,
                                          @RequestParam String startDate,
                                          @RequestParam String endDate){
        if(startDate == null || endDate == null){
            return null;
        }
        Date parse = DateUtil.parse(endDate);
        Date endDate2 = DateUtil.offsetMonth(parse, 1);
        String endDate3 = DateUtil.format(endDate2, "yyyy-MM-dd");

        Date parse2 = DateUtil.parse(startDate);
        Date startDate2 = DateUtil.offsetMonth(parse2, 1);
        String startDate3 = DateUtil.format(startDate2, "yyyy-MM-dd");
        PageEntity<OrderFinanceDTO> pageEntity = orderService.listOrder(new Page<>(page, count), customerId, startDate3, endDate3);
        return pageEntity;
    }
}
