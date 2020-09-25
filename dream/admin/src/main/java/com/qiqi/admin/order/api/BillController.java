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
    public PageEntity<OrderDO> getAllBill(@RequestParam(value = "page",defaultValue = "1") Long page,
                                          @RequestParam(value = "count",defaultValue = "10") Long count,
                                          @RequestParam(required = false) Long customerId,
                                          @RequestParam Date startDate,
                                          @RequestParam Date endDate){
        List<TitleVO> titleList = new ArrayList<>();
        titleList.add(new TitleVO("客户","name"));
        long month = DateUtil.betweenMonth(startDate, endDate, true)+1;
        for (int i = 0; i < month; i++) {
            DateTime dataTime = DateUtil.offset(startDate, DateField.MONTH, i);
            titleList.add(new TitleVO(DateUtil.year(dataTime)+"年"+(DateUtil.month(dataTime)+1)+"月",DateUtil.year(dataTime)+"年"+(DateUtil.month(dataTime)+1)+"月"));
        }
        QueryWrapper<OrderDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!ObjectUtils.isEmpty(customerId),"customer_id",customerId)
                .between(!ObjectUtils.isEmpty(startDate)&&!ObjectUtils.isEmpty(endDate),"delivery_date",startDate,endDate);
        IPage<OrderDO> iPage = orderService.page(new Page<>(page,count),queryWrapper);
        List<BillsDTO> allBill = Convert.convert(new TypeReference<List<BillsDTO>>() {}, iPage.getRecords());
        JSONArray jsonArray = new JSONArray();
        Map<Long, List<BillsDTO>> customerMap = allBill.stream().collect(Collectors.groupingBy(BillsDTO::getCustomerId));
        List<String> label = new ArrayList<>();
        titleList.forEach(x->label.add(x.getLabel()));
        label.remove(0);
        customerMap.forEach((key, value) -> {
            value.forEach(item -> {
                item.setGroudBy(DateUtil.year(item.getDeliveryDate())+"年"+(DateUtil.month(item.getDeliveryDate())+1)+"月");
            });
            Map<String, List<BillsDTO>> collect = value.stream().collect(Collectors.groupingBy(BillsDTO::getGroudBy));
            JSONObject json = new JSONObject();
            json.put("name",value.get(0).getName());
            final BigDecimal[] total = {new BigDecimal("0.00")};
            collect.forEach((key1, value2) -> {
                List<BigDecimal> list1 = new ArrayList();
                BigDecimal money = value2.stream().map(data -> data.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal beginReceive = value2.stream().map(data -> data.getBeginReceive()).reduce(BigDecimal.ZERO, BigDecimal::add);
                total[0] = total[0].add(money);
                list1.add(money);
                list1.add(beginReceive);
                list1.add(money.subtract(beginReceive));
                json.put(key1,list1);
            });
            json.put("total",total);
            for (String s : label) {
                if(!json.containsKey(s)){
                    BigDecimal[] bigDecimals = new BigDecimal[3];
                    bigDecimals[0] = BigDecimal.valueOf(0);
                    bigDecimals[1] = BigDecimal.valueOf(0);
                    bigDecimals[2] = BigDecimal.valueOf(0);
                    json.put(s,bigDecimals);
                }
            }
            jsonArray.add(json);
        });
        titleList.add(new TitleVO("合计","total"));
        Map<String,Object> result = new HashMap<>();
        result.put("title",titleList);
        result.put("data",jsonArray);
        System.out.println(JSONObject.toJSONString(result));
        return new PageEntity<>(iPage.getTotal(), Convert.convert(new TypeReference<List<OrderDO>>() {}, result));
    }

    @ApiOperation(value = "总计")
    @GetMapping("/total")
    public List<TotalVO> getTotal(@RequestParam String startDate,
                                  @RequestParam String endDate,
                                  @RequestParam(required = false) Long customerId){
        return orderService.getTotal(startDate,endDate,customerId);
    }
}
