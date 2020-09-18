package com.qiqi.admin.basicdata.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.basicdata.dto.CustomerQuotationDTO;
import com.qiqi.basicdata.entity.CustomerQuotationDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.basicdata.entity.PaperboardDataSettingDO;
import com.qiqi.basicdata.service.PaperboardDataSettingService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.basicdata.service.CustomerQuotationService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 客户报价单 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Api("客户报价单相关接口")
@RestController
@RequestMapping("/customer/quotation")
public class CustomerQuotationController {

    @Resource
    private CustomerQuotationService customerQuotationService;

    @Resource
    private PaperboardDataSettingService paperboardDataSettingService;

    @ApiOperation(value = "报价单列表分页")
    @PostMapping("/list")
    public PageEntity<CustomerQuotationDO> getCustomerQuotationPage(@RequestBody CustomerQuotationDTO query) {
        QueryWrapper<CustomerQuotationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getCode()),"code",query.getCode())
                .like(StringUtils.isNotBlank(query.getShorts()),"shorts",query.getShorts())
                .like(StringUtils.isNotBlank(query.getFullName()),"full_name",query.getFullName());
        IPage<CustomerQuotationDO> iPage = customerQuotationService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<CustomerQuotationDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "纸板资料列表")
    @GetMapping("/paper")
    public List<PaperboardDataSettingDO> getPaper(){
        return paperboardDataSettingService.list();
    }

    @ApiOperation(value = "获取客户报价单(单个)")
    @GetMapping("/{id}")
    public CustomerQuotationDO getCustomerQuotation(@PathVariable Long id) {
        //todo: 需要转Vo

        return customerQuotationService.getById(id);
    }

    @ApiOperation(value = "修改客户报价单")
    @PutMapping("")
    public Boolean updateCustomerQuotation(@RequestBody CustomerQuotationDO customerQuotationDO) {
        return customerQuotationService.updateById(customerQuotationDO);
    }

    @ApiOperation(value = "新增客户报价单")
    @PostMapping("")
    public Boolean saveCustomerQuotation(@RequestBody CustomerQuotationDO customerQuotationDO) {
        return customerQuotationService.saveOrUpdate(customerQuotationDO);
    }

    @ApiOperation(value = "删除客户报价单(批量))")
    @DeleteMapping("")
    public Boolean deleteCustomerQuotationByIds(@RequestBody List<Long> idList) {
        return customerQuotationService.removeByIds(idList);
    }

    @ApiOperation(value = "删除客户报价单(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteCustomerQuotationById(@PathVariable Long id) {
        return customerQuotationService.removeById(id);
    }
}
