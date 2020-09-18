package com.qiqi.admin.basicdata.api;

import com.qiqi.admin.basicdata.model.SupplierCardboardQuotationVO;
import com.qiqi.basicdata.entity.SupplierCardboardQuotationDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.basicdata.service.PaperboardDataSettingService;
import com.qiqi.basicdata.service.SupplierService;
import com.qiqi.order.service.OrderService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.basicdata.service.SupplierCardboardQuotationService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 供应商纸板报价 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Api("供应商纸板报价相关接口")
@RestController
@RequestMapping("/supplier/cardboard/quotation")
public class SupplierCardboardQuotationController {

    @Resource
    private SupplierCardboardQuotationService supplierCardboardQuotationService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private PaperboardDataSettingService paperboardDataSettingService;
    @Resource
    private OrderService orderService;

    @ApiOperation(value = "获取供应商纸板报价(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<SupplierCardboardQuotationVO> getSupplierCardboardQuotationPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                                                      @RequestParam(value = "size",defaultValue = "10") Long size,
                                                                                      @RequestParam(value = "code") String code,
                                                                                      @RequestParam(value = "abbreviation") String abbreviation,
                                                                                      @RequestParam(value = "time") String time) {
        IPage iPage = supplierCardboardQuotationService.findList(code,abbreviation,time,new Page<>(page,size));
        //todo: 需要转Vo
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<SupplierCardboardQuotationVO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取供应商纸板报价(单个)")
    @GetMapping("/{id}")
    public SupplierCardboardQuotationDO getSupplierCardboardQuotation(@PathVariable Long id) {
        //todo: 需要转Vo

        return supplierCardboardQuotationService.getById(id);
    }

    @ApiOperation(value = "修改供应商纸板报价")
    @PutMapping("")
    public Boolean updateSupplierCardboardQuotation(@RequestBody SupplierCardboardQuotationDO supplierCardboardQuotationDO) {
        return supplierCardboardQuotationService.updateById(supplierCardboardQuotationDO);
    }

    @ApiOperation(value = "新增供应商纸板报价")
    @PostMapping("/add")
    public Boolean saveSupplierCardboardQuotation(@RequestBody SupplierCardboardQuotationDO supplierCardboardQuotationDO) {
        return supplierCardboardQuotationService.saveOrUpdate(supplierCardboardQuotationDO);
    }

    @ApiOperation(value = "删除供应商纸板报价(批量))")
    @DeleteMapping("")
    public Boolean deleteSupplierCardboardQuotationByIds(@RequestBody List<Long> idList) {
        return supplierCardboardQuotationService.removeByIds(idList);
    }

    @ApiOperation(value = "删除供应商纸板报价(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteSupplierCardboardQuotationById(@PathVariable Long id) {
        return supplierCardboardQuotationService.removeById(id);
    }

    /**
     * 下拉框
     * @param
     * @return {@link List}
     * @author sun
     * @time 2020/9/15 14:15
     */
    @GetMapping("/pagerSelect")
    public List pagerSelect(){
        return paperboardDataSettingService.list();
    }

    /**
     * 下拉框
     * @param
     * @return {@link List}
     * @author sun
     * @time 2020/9/15 14:15
     */
    @GetMapping("/supplierSelect")
    public List supplierSelect(){
        return supplierService.list();
    }

    /**
     * 下拉框
     * @param
     * @return {@link List}
     * @author sun
     * @time 2020/9/15 14:15
     */
    @GetMapping("/customerSelect")
    public List customerSelect(){
        return orderService.list();
    }


}
