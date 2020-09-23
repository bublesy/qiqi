package com.qiqi.admin.cardboardinventory.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiqi.cardboardinvenroty.entity.CardboardInventoryDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.purchase.entity.PurchaseOrderDO;
import io.swagger.annotations.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.cardboardinvenroty.service.CardboardInventoryService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 纸板库存 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Api("纸板库存相关接口")
@RestController
@RequestMapping("/cardboard/inventory")
public class CardboardInventoryController {

    @Resource
    private CardboardInventoryService cardboardInventoryService;

    @ApiOperation(value = "获取纸板库存(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<CardboardInventoryDO> getCardboardInventoryPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                                      @RequestParam(value = "size",defaultValue = "10") Long size,
                                                                      @RequestParam(value = "time") String time) {
        LambdaQueryWrapper<CardboardInventoryDO> wrapper = new LambdaQueryWrapper<CardboardInventoryDO>();
        wrapper.like(!ObjectUtils.isEmpty(time),CardboardInventoryDO::getCreatedTime,time);
        IPage<CardboardInventoryDO> iPage = cardboardInventoryService.page(new Page<>(page,size),wrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<CardboardInventoryDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取纸板库存(单个)")
    @GetMapping("/{id}")
    public CardboardInventoryDO getCardboardInventory(@PathVariable Long id) {
        //todo: 需要转Vo

        return cardboardInventoryService.getById(id);
    }

    @ApiOperation(value = "修改纸板库存")
    @PutMapping("")
    public Boolean updateCardboardInventory(@RequestBody CardboardInventoryDO cardboardInventoryDO) {
        return cardboardInventoryService.updateById(cardboardInventoryDO);
    }

    @ApiOperation(value = "新增纸板库存")
    @PostMapping("/add")
    public Boolean saveCardboardInventory(@RequestBody CardboardInventoryDO cardboardInventoryDO) {
        return cardboardInventoryService.saveOrUpdate(cardboardInventoryDO);
    }

    @ApiOperation(value = "删除纸板库存(批量))")
    @DeleteMapping("")
    public Boolean deleteCardboardInventoryByIds(@RequestBody List<Long> idList) {
        return cardboardInventoryService.removeByIds(idList);
    }

    @ApiOperation(value = "删除纸板库存(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteCardboardInventoryById(@PathVariable Long id) {
        return cardboardInventoryService.removeById(id);
    }
}
