package com.qiqi.admin.basicdata.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.basicdata.dto.customerDTO;
import com.qiqi.basicdata.entity.CustomerInformationDO;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.basicdata.service.CustomerInformationService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 客户资料 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Api("客户资料相关接口")
@RestController
@RequestMapping("/customer/information")
public class CustomerInformationController {

    @Resource
    private CustomerInformationService customerInformationService;

    @ApiOperation(value = "获取客户资料(列表)")
    @PostMapping("/list")
    public PageEntity<CustomerInformationDO> getCustomerInformationPage(@RequestBody customerDTO query) {
        boolean a = StringUtils.isNotBlank(query.getCode());
        QueryWrapper queryWrapper = new QueryWrapper<CustomerInformationDO>()
                .like(StringUtils.isNotBlank(query.getCode()),"code",query.getCode())
                .like(StringUtils.isNotBlank(query.getShorts()),"shorts",query.getShorts())
                .like(StringUtils.isNotBlank(query.getPhone()),"phone",query.getPhone())
                .like(StringUtils.isNotBlank(query.getMobilePhone()),"mobile_phone",query.getMobilePhone());
        IPage<CustomerInformationDO> iPage = customerInformationService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<CustomerInformationDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取客户资料(单个)")
    @GetMapping("/{id}")
    public CustomerInformationDO getCustomerInformation(@PathVariable Long id) {
        //todo: 需要转Vo

        return customerInformationService.getById(id);
    }

    @ApiOperation(value = "修改客户资料")
    @PutMapping("")
    public Boolean updateCustomerInformation(@RequestBody CustomerInformationDO customerInformationDO) {
        return customerInformationService.updateById(customerInformationDO);
    }

    @ApiOperation(value = "新增客户资料")
    @PostMapping("")
    public Boolean saveCustomerInformation(@RequestBody CustomerInformationDO customerInformationDO) {
        try {
            return customerInformationService.saveOrUpdate(customerInformationDO);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ApiOperation(value = "删除客户资料(批量))")
    @DeleteMapping("")
    public Boolean deleteCustomerInformationByIds(@RequestBody List<Long> idList) {
        return customerInformationService.removeByIds(idList);
    }

    @ApiOperation(value = "删除客户资料(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteCustomerInformationById(@PathVariable Long id) {
        return customerInformationService.removeById(id);
    }
}
