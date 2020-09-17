package com.qiqi.admin.basicdata.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiqi.admin.basicdata.model.SupplierVO;
import com.qiqi.basicdata.entity.SupplierCardboardQuotationDO;
import com.qiqi.basicdata.entity.SupplierDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.basicdata.service.SupplierCardboardQuotationService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.basicdata.service.SupplierService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 供应商 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Api("供应商相关接口")
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Resource
    private SupplierService supplierService;
    @Resource
    private SupplierCardboardQuotationService supplierCardboardQuotationService;

    @ApiOperation(value = "获取供应商(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<SupplierVO> getSupplierPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                    @RequestParam(value = "size",defaultValue = "10") Long size,
                                                    @RequestParam(value = "code") String code,
                                                    @RequestParam(value = "abbreviation") String abbreviation,
                                                    @RequestParam(value = "time") String time) {
        //todo: 需要转Vo
        LambdaQueryWrapper<SupplierDO> wrapper = new LambdaQueryWrapper<SupplierDO>();
        wrapper.like(!ObjectUtils.isEmpty(code),SupplierDO::getCode,code);
        wrapper.like(!ObjectUtils.isEmpty(time),SupplierDO::getCreatedTime,time);
        wrapper.like(!ObjectUtils.isEmpty(abbreviation),SupplierDO::getAbbreviation,abbreviation);
        IPage<SupplierDO> iPage = supplierService.page(new Page<>(page,size),wrapper);
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<SupplierVO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取供应商(单个)")
    @GetMapping("/{id}")
    public SupplierDO getSupplier(@PathVariable Long id) {
        //todo: 需要转Vo

        return supplierService.getById(id);
    }

    @ApiOperation(value = "修改供应商")
    @PutMapping("")
    public Boolean updateSupplier(@RequestBody SupplierDO supplierDO) {
        return supplierService.updateById(supplierDO);
    }

    @ApiOperation(value = "新增供应商")
    @PostMapping("/add")
    public Boolean saveSupplier(@RequestBody SupplierDO supplierDO) {
        SupplierCardboardQuotationDO supplierCardboardQuotationDO = new SupplierCardboardQuotationDO();
        BeanUtils.copyProperties(supplierDO,supplierCardboardQuotationDO);
        if (supplierDO.getId()==null ){
            supplierCardboardQuotationService.save(supplierCardboardQuotationDO);
        }else{
            supplierCardboardQuotationService.update();
        }
        return supplierService.saveOrUpdate(supplierDO);
    }

    @ApiOperation(value = "删除供应商(批量))")
    @DeleteMapping("")
    public Boolean deleteSupplierByIds(@RequestBody List<Long> idList) {
        return supplierService.removeByIds(idList);
    }

    @ApiOperation(value = "删除供应商(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteSupplierById(@PathVariable Long id) {
        return supplierService.removeById(id);
    }
}
