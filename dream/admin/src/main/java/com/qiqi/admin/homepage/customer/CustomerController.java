package com.qiqi.admin.homepage.customer;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.basicdata.entity.CustomerInformationDO;
import com.qiqi.basicdata.service.CustomerInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author hc
 * @desc
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/home")
@Api(tags = "首页客户相关接口")
public class CustomerController {

    @Resource
    private CustomerInformationService customerInformationService;

    @ApiOperation("客户总数")
    @GetMapping("/customerTotal")
    public Integer getCustomerTotal(){

        return customerInformationService.count();
    }

    @ApiOperation("较昨日新增客户数")
    @GetMapping("/add")
    public Integer getAdd(){
        String yesterday = DateUtil.format(DateUtil.yesterday(), "yyyy-MM-dd");
        String today = DateUtil.format(new Date(),"yyyy-MM-dd");
        int count = customerInformationService.count(new QueryWrapper<CustomerInformationDO>()
                .apply("date_format (created_time,'%Y-%m-%d') <= '" + yesterday + "'"));
        int count1 = customerInformationService.count(new QueryWrapper<CustomerInformationDO>()
                .apply("date_format (created_time,'%Y-%m-%d') <= '" + today + "'"));
        return count1 - count;
    }


}
