package com.qiqi.admin.order.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.finance.entity.CustomerDetailDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.finance.service.CustomerDetailService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-26
 */
@Api("相关接口")
@RestController
@RequestMapping("/customer/detail")
public class CustomerDetailController {

    @Resource
    private CustomerDetailService customerDetailService;



    @ApiOperation(value = "月结对账单过账")
    @GetMapping("/post")
    public Boolean post(@RequestParam Long id,@RequestParam String post){
        CustomerDetailDO customerDetailDO = new CustomerDetailDO();
        customerDetailDO.setOrderId(id);
        customerDetailDO.setPost(post);
        return customerDetailService.update(customerDetailDO,new QueryWrapper<CustomerDetailDO>()
                .eq(!ObjectUtils.isEmpty(id),"order_id",id));
    }

    @ApiOperation(value = "结算")
    @GetMapping("/settlement")
    public Boolean settlement(@RequestParam Long id, @RequestParam String settlement, @RequestParam BigDecimal payed){
        CustomerDetailDO customerDetailDO = new CustomerDetailDO();
        customerDetailDO.setOrderId(id);
        customerDetailDO.setPayed(payed);
        customerDetailDO.setSettlement(settlement);
        return customerDetailService.update(customerDetailDO,new QueryWrapper<CustomerDetailDO>()
                .eq(!ObjectUtils.isEmpty(id),"order_id",id));
    }

    @ApiOperation(value = "获取(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<CustomerDetailDO> getCustomerDetailPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                        @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<CustomerDetailDO> iPage = customerDetailService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<CustomerDetailDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取(单个)")
    @GetMapping("/{id}")
    public CustomerDetailDO getCustomerDetail(@PathVariable Long id) {
        //todo: 需要转Vo

        return customerDetailService.getOne(new QueryWrapper<CustomerDetailDO>().eq(!ObjectUtils.isEmpty(id),"order_id",id));
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Boolean updateCustomerDetail(@RequestBody CustomerDetailDO customerDetailDO) {
        return customerDetailService.updateById(customerDetailDO);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Boolean saveCustomerDetail(@RequestBody CustomerDetailDO customerDetailDO) {
        return customerDetailService.save(customerDetailDO);
    }

    @ApiOperation(value = "删除(批量))")
    @DeleteMapping("")
    public Boolean deleteCustomerDetailByIds(@RequestBody List<Long> idList) {
        return customerDetailService.removeByIds(idList);
    }

    @ApiOperation(value = "删除(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteCustomerDetailById(@PathVariable Long id) {
        return customerDetailService.removeById(id);
    }
}
