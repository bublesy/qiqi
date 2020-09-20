package com.qiqi.admin.material.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiqi.admin.purchase.model.PurchaseOrderVO;
import com.qiqi.material.entity.MaterialInventoryDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.purchase.entity.PurchaseOrderDO;
import io.swagger.annotations.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.material.service.MaterialInventoryService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 辅料库存 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-20
 */
@Api("辅料库存相关接口")
@RestController
@RequestMapping("/material/inventory")
public class MaterialInventoryController {

    @Resource
    private MaterialInventoryService materialInventoryService;

    @ApiOperation(value = "获取辅料库存(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<MaterialInventoryDO> getMaterialInventoryPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Long size,
                                                                    @RequestParam(value = "time") String time) {

        //todo: 需要转Vo
        LambdaQueryWrapper<MaterialInventoryDO> wrapper = new LambdaQueryWrapper<MaterialInventoryDO>();
        wrapper.like(!ObjectUtils.isEmpty(time),MaterialInventoryDO::getCreatedTime,time);
        IPage<MaterialInventoryDO> iPage = materialInventoryService.page(new Page<>(page,size),wrapper);
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<MaterialInventoryDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取辅料库存(单个)")
    @GetMapping("/{id}")
    public MaterialInventoryDO getMaterialInventory(@PathVariable Long id) {
        //todo: 需要转Vo

        return materialInventoryService.getById(id);
    }

    @ApiOperation(value = "修改辅料库存")
    @PutMapping("")
    public Boolean updateMaterialInventory(@RequestBody MaterialInventoryDO materialInventoryDO) {
        return materialInventoryService.updateById(materialInventoryDO);
    }

    @ApiOperation(value = "新增辅料库存")
    @PostMapping("")
    public Boolean saveMaterialInventory(@RequestBody MaterialInventoryDO materialInventoryDO) {
        return materialInventoryService.save(materialInventoryDO);
    }

    @ApiOperation(value = "删除辅料库存(批量))")
    @DeleteMapping("")
    public Boolean deleteMaterialInventoryByIds(@RequestBody List<Long> idList) {
        return materialInventoryService.removeByIds(idList);
    }

    @ApiOperation(value = "删除辅料库存(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteMaterialInventoryById(@PathVariable Long id) {
        return materialInventoryService.removeById(id);
    }
}
