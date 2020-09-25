package com.qiqi.admin.order.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.admin.order.dto.BillDTO;
import com.qiqi.admin.order.dto.BillVO;
import com.qiqi.basicdata.entity.CustomerInformationDO;
import com.qiqi.basicdata.service.CustomerInformationService;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.dto.BillsDTO;
import com.qiqi.order.dto.OrderFinanceDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import com.qiqi.order.vo.TitleVO;
import com.qiqi.order.vo.TotalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    public PageEntity<OrderFinanceDTO> getAllBill(@RequestParam(value = "page",defaultValue = "1") Long page,
                                          @RequestParam(value = "count",defaultValue = "10") Long count,
                                          @RequestParam(required = false) Long customerId,
                                          @RequestParam String startDate,
                                          @RequestParam String endDate){
        if(startDate == null || endDate == null){
            return null;
        }

        PageEntity<OrderFinanceDTO> pageEntity = orderService.listOrder(new Page<>(page, count), customerId, startDate, endDate);
        return pageEntity;
    }

//    @ApiOperation(value = "总计")
//    @GetMapping("/total")
//    public List<TotalVO> getTotal(@RequestParam String startDate,
//                                  @RequestParam String endDate,
//                                  @RequestParam(required = false) Long customerId){
//        return orderService.getTotal(startDate,endDate,customerId);
//    }
}
