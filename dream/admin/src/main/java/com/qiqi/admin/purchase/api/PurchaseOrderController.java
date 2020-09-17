package com.qiqi.admin.purchase.api;

import com.qiqi.purchase.entity.PurchaseOrderDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.purchase.service.PurchaseOrderService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 采购单 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api("采购单相关接口")
@RestController
@RequestMapping("/purchase/order")
public class PurchaseOrderController {

    @Resource
    private PurchaseOrderService purchaseOrderService;

    @ApiOperation(value = "获取采购单(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<PurchaseOrderDO> getPurchaseOrderPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                        @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<PurchaseOrderDO> iPage = purchaseOrderService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<PurchaseOrderDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取采购单(单个)")
    @GetMapping("/{id}")
    public PurchaseOrderDO getPurchaseOrder(@PathVariable Long id) {
        //todo: 需要转Vo

        return purchaseOrderService.getById(id);
    }

    @ApiOperation(value = "修改采购单")
    @PutMapping("")
    public Boolean updatePurchaseOrder(@RequestBody PurchaseOrderDO purchaseOrderDO) {
        return purchaseOrderService.updateById(purchaseOrderDO);
    }

    @ApiOperation(value = "新增采购单")
    @PostMapping("/add")
    public Boolean savePurchaseOrder(@RequestBody PurchaseOrderDO purchaseOrderDO) {
        return purchaseOrderService.saveOrUpdate(purchaseOrderDO);
    }

    @ApiOperation(value = "删除采购单(批量))")
    @DeleteMapping("")
    public Boolean deletePurchaseOrderByIds(@RequestBody List<Long> idList) {
        return purchaseOrderService.removeByIds(idList);
    }

    @ApiOperation(value = "删除采购单(单个))")
    @DeleteMapping("/{id}")
    public Boolean deletePurchaseOrderById(@PathVariable Long id) {
        return purchaseOrderService.removeById(id);
    }
}
