package com.qiqi.admin.materialspare.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.cardboardinvenroty.entity.CardboardInventoryDO;
import com.qiqi.materialspare.entity.MaterialSpareDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.materialspare.service.MaterialSpareService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 材料备用库存 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Api("材料备用库存相关接口")
@RestController
@RequestMapping("/material/spare")
public class MaterialSpareController {

    @Resource
    private MaterialSpareService materialSpareService;

    @ApiOperation(value = "获取材料备用库存(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<MaterialSpareDO> getMaterialSparePage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                            @RequestParam(value = "count",defaultValue = "10") Long count,
                                                            @RequestParam(value = "time") String time,
                                                            @RequestParam(value = "material") String material) {
        QueryWrapper<MaterialSpareDO> wrapper = new QueryWrapper<MaterialSpareDO>();
        wrapper.like(!ObjectUtils.isEmpty(time),"created_time",time)
                .like(!ObjectUtils.isEmpty(material),"material_id",material);
        IPage<MaterialSpareDO> iPage = materialSpareService.page(new Page<>(page,count),wrapper);
        //todo: 需要转Vo
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<MaterialSpareDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取材料备用库存(单个)")
    @GetMapping("/{id}")
    public MaterialSpareDO getMaterialSpare(@PathVariable Long id) {
        //todo: 需要转Vo

        return materialSpareService.getById(id);
    }

    @ApiOperation(value = "修改材料备用库存")
    @PutMapping("")
    public Boolean updateMaterialSpare(@RequestBody MaterialSpareDO materialSpareDO) {
        return materialSpareService.updateById(materialSpareDO);
    }

    @ApiOperation(value = "新增材料备用库存")
    @PostMapping("/add")
    public Boolean saveMaterialSpare(@RequestBody MaterialSpareDO materialSpareDO) {
        return materialSpareService.saveOrUpdate(materialSpareDO);
    }

    @ApiOperation(value = "删除材料备用库存(批量))")
    @DeleteMapping("")
    public Boolean deleteMaterialSpareByIds(@RequestBody List<Long> idList) {
        return materialSpareService.removeByIds(idList);
    }

    @ApiOperation(value = "删除材料备用库存(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteMaterialSpareById(@PathVariable Long id) {
        return materialSpareService.removeById(id);
    }
}
