package com.qiqi.admin.order.api;

import com.qiqi.order.entity.DeliveryNoteDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.order.service.DeliveryNoteService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 送货单 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-10-08
 */
@Api("送货单相关接口")
@RestController
@RequestMapping("/delivery/note")
public class DeliveryNoteController {

    @Resource
    private DeliveryNoteService deliveryNoteService;

    @ApiOperation(value = "获取送货单(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<DeliveryNoteDO> getDeliveryNotePage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                        @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<DeliveryNoteDO> iPage = deliveryNoteService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<DeliveryNoteDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取送货单(单个)")
    @GetMapping("/{id}")
    public DeliveryNoteDO getDeliveryNote(@PathVariable Long id) {
        //todo: 需要转Vo

        return deliveryNoteService.getById(id);
    }

    @ApiOperation(value = "修改送货单")
    @PutMapping("")
    public Boolean updateDeliveryNote(@RequestBody DeliveryNoteDO deliveryNoteDO) {
        return deliveryNoteService.updateById(deliveryNoteDO);
    }

    @ApiOperation(value = "新增送货单")
    @PostMapping("")
    public Boolean saveDeliveryNote(@RequestBody DeliveryNoteDO deliveryNoteDO) {
        return deliveryNoteService.save(deliveryNoteDO);
    }

    @ApiOperation(value = "删除送货单(批量))")
    @DeleteMapping("")
    public Boolean deleteDeliveryNoteByIds(@RequestBody List<Long> idList) {
        return deliveryNoteService.removeByIds(idList);
    }

    @ApiOperation(value = "删除送货单(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteDeliveryNoteById(@PathVariable Long id) {
        return deliveryNoteService.removeById(id);
    }
}
