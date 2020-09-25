package com.qiqi.admin;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiqi.common.entity.SelectOption;
import com.qiqi.order.dto.BillsDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import com.qiqi.order.vo.TitleVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootTest
class AdminApplicationTests {

    @Resource
    private OrderService orderService;
    @Test
    void contextLoads() {

        List<TitleVO> titleList = new ArrayList<>();
        titleList.add(new TitleVO("客户","name"));
//        titleList.add(new TitleVO("前期","first"));

        String dateStr = "2020-08-01";
        Date date = DateUtil.parse(dateStr, "yyyy-MM-dd");

        String dateStr1 = "2020-12-02";
        Date date1 = DateUtil.parse(dateStr1, "yyyy-MM-dd");
        long month = DateUtil.betweenMonth(date, date1, true)+1;

        for (int i = 0; i < month; i++) {
            DateTime dataTime = DateUtil.offset(date1, DateField.MONTH, i);
            titleList.add(new TitleVO(DateUtil.year(dataTime)+"年"+(DateUtil.month(dataTime)+1)+"月",DateUtil.year(dataTime)+"年"+(DateUtil.month(dataTime)+1)+"月"));
        }
        //--------------------------------------------------------------------------------------------------------------------------------------
        List<OrderDO> list = orderService.list();
        List<BillsDTO> allBill = Convert.convert(new TypeReference<List<BillsDTO>>() {}, list);

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
            json.put("total", total);
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
    }

    public static Boolean isExistField(String field, Object obj) {
        if (obj == null || StringUtils.isEmpty(field)) {
            return null;
        }
        Object o = JSON.toJSON(obj);
        JSONObject jsonObj = new JSONObject();
        if (o instanceof JSONObject) {
            jsonObj = (JSONObject) o;
        }
        return jsonObj.containsKey(field);
    }

}
