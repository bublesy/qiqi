package com.qiqi.admin.homepage.order;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiqi.common.base.BaseModel;
import com.qiqi.finance.entity.CustomerDetailDO;
import com.qiqi.finance.service.CustomerDetailService;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hc
 * @desc
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/home")
@Api(tags = "首页订单相关接口")
public class OrderHomeController {

    @Resource
    private OrderService orderService;

    @Resource
    private CustomerDetailService customerDetailService;

    @ApiOperation("营业额")
    @GetMapping("/orderTotal")
    public JSONArray getOrderTotal(@RequestParam @JsonFormat(pattern = "yyyy-MM-dd")Date date, Integer type){
        JSONArray jsonArray = new JSONArray();
        switch (type){
            case 1:
                Map<Integer, List<OrderDO>> collect = orderService.list(new LambdaQueryWrapper<OrderDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfDay(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfDay(date)))
                        .stream().collect(Collectors.groupingBy(data -> DateUtil.hour(data.getCreatedTime(), true)));
                for (int i = 1; i <= 24; i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("小时",i+"");

                    jsonObject.putOpt("营业额", ObjectUtils.isEmpty(collect.get(i))?0:collect.get(i).stream().map(data -> data.getMoney())
                            .reduce(BigDecimal.ZERO, BigDecimal::add));
                    jsonArray.put(jsonObject);
                }
                break;
            case 2:
                Map<Integer, List<OrderDO>> collect1 = orderService.list(new LambdaQueryWrapper<OrderDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfMonth(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfMonth(date)))
                        .stream().collect(Collectors.groupingBy(data -> DateUtil.dayOfMonth(data.getCreatedTime())));
                int days = getDays(DateUtil.year(date),DateUtil.month(date)+1);
                for (int i = 1; i <= days ; i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("月份",i+"");
                    jsonObject.putOpt("营业额",ObjectUtils.isEmpty(collect1.get(i))?0:collect1.get(i).stream().map(data -> data.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    jsonArray.put(jsonObject);
                }
                break;
            case 3:
                Map<Integer, List<OrderDO>> collect2 = orderService.list(new LambdaQueryWrapper<OrderDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfYear(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfYear(date)))
                        .stream().collect(Collectors.groupingBy(data -> DateUtil.month(data.getCreatedTime())));
                for (int i = 0; i < 12; i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("年份",(i+1)+"");
                    jsonObject.putOpt("营业额",ObjectUtils.isEmpty(collect2.get(i))?0:collect2.get(i).stream().map(data -> data.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    jsonArray.put(jsonObject);
                }
                break;
            default:
                return jsonArray.put("参数错误");
        }
        return jsonArray;
    }

    public static int getDays(int year, int month){
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    @ApiOperation("订单总金额")
    @GetMapping("/orderMoneyTotal")
    public BigDecimal getOrderMoneyTotal(@RequestParam @JsonFormat(pattern = "yyyy-MM-dd")Date date, Integer type){
        switch (type) {
            case 1:
                List<OrderDO> list1 = orderService.list(new LambdaQueryWrapper<OrderDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfDay(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfDay(date)));
                BigDecimal total = list1.stream().map(data -> data.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                return total;
            case 2:
                List<OrderDO> list3 = orderService.list(new LambdaQueryWrapper<OrderDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfMonth(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfMonth(date)));
                BigDecimal total3 = list3.stream().map(data -> data.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                return total3;
            case 3:
                List<OrderDO> list5 = orderService.list(new LambdaQueryWrapper<OrderDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfYear(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfYear(date)));
                BigDecimal total5 = list5.stream().map(data -> data.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                return total5;
            default:
                return null;
        }
    }

    @ApiOperation("订单已付款")
    @GetMapping("/orderPayed")
    public BigDecimal getOrderPayed(@RequestParam @JsonFormat(pattern = "yyyy-MM-dd")Date date, Integer type){
        // type:1代表日,2代表月,3代表年
        switch (type){
            case 1:
                List<CustomerDetailDO> list = customerDetailService.list(new LambdaQueryWrapper<CustomerDetailDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfDay(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfDay(date)));
                return list.stream().map(data -> data.getPayed() == null ? BigDecimal.ZERO : data.getPayed()).reduce(BigDecimal.ZERO, BigDecimal::add);
            case 2:
                List<CustomerDetailDO> list1 = customerDetailService.list(new LambdaQueryWrapper<CustomerDetailDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfMonth(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfMonth(date)));
                return list1.stream().map(data -> data.getPayed() == null ? BigDecimal.ZERO : data.getPayed()).reduce(BigDecimal.ZERO, BigDecimal::add);
            case 3:
                List<CustomerDetailDO> list2 = customerDetailService.list(new LambdaQueryWrapper<CustomerDetailDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfYear(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfYear(date)));
                return list2.stream().map(data -> data.getPayed() == null ? BigDecimal.ZERO : data.getPayed()).reduce(BigDecimal.ZERO, BigDecimal::add);
            default:
                return null;
        }
    }

    @ApiOperation("订单未付款")
    @GetMapping("/orderUnPayed")
    public BigDecimal getOrderUnPayed(@RequestParam @JsonFormat(pattern = "yyyy-MM-dd")Date date, Integer type) {
        switch (type) {
            case 1:
                List<OrderDO> list1 = orderService.list(new LambdaQueryWrapper<OrderDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfDay(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfDay(date)));
                List<CustomerDetailDO> list2 = customerDetailService.list(new LambdaQueryWrapper<CustomerDetailDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfDay(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfDay(date)));
                BigDecimal total = list1.stream().map(data -> data.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal payed = list2.stream().map(data -> data.getPayed() == null ? BigDecimal.ZERO : data.getPayed()).reduce(BigDecimal.ZERO, BigDecimal::add);
                return total.subtract(payed);
            case 2:
                List<OrderDO> list3 = orderService.list(new LambdaQueryWrapper<OrderDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfMonth(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfMonth(date)));
                List<CustomerDetailDO> list4 = customerDetailService.list(new LambdaQueryWrapper<CustomerDetailDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfMonth(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfMonth(date)));
                BigDecimal total3 = list3.stream().map(data -> data.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal payed4 = list4.stream().map(data -> data.getPayed() == null ? BigDecimal.ZERO : data.getPayed()).reduce(BigDecimal.ZERO, BigDecimal::add);
                return total3.subtract(payed4);
            case 3:
                List<OrderDO> list5 = orderService.list(new LambdaQueryWrapper<OrderDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfYear(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfYear(date)));
                List<CustomerDetailDO> list6 = customerDetailService.list(new LambdaQueryWrapper<CustomerDetailDO>()
                        .ge(BaseModel::getCreatedTime, DateUtil.beginOfYear(date))
                        .le(BaseModel::getCreatedTime, DateUtil.endOfYear(date)));
                BigDecimal total5 = list5.stream().map(data -> data.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal payed6 = list6.stream().map(data -> data.getPayed() == null ? BigDecimal.ZERO : data.getPayed()).reduce(BigDecimal.ZERO, BigDecimal::add);
                return total5.subtract(payed6);
            default:
                return null;
        }
    }
}
