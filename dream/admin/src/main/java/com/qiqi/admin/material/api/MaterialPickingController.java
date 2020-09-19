package com.qiqi.admin.material.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.admin.material.dto.MaterialPickingDTO;
import com.qiqi.material.entity.MaterialInStoreDO;
import com.qiqi.material.entity.MaterialPickingDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.material.service.MaterialPickingService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Api(tags = "辅料领料相关接口")
@RestController
@RequestMapping("/material/picking")
public class MaterialPickingController {

    @Resource
    private MaterialPickingService materialPickingService;

    @ApiOperation(value = "获取(列表)")
    @PostMapping("/list")
    public PageEntity<MaterialPickingDO> getMaterialPickingPage(@RequestBody MaterialPickingDTO query) {
        QueryWrapper<MaterialPickingDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!ObjectUtils.isEmpty(query.getDate()),"date",query.getDate())
                .like(StringUtils.isNotBlank(query.getPartOrder()),"part_order",query.getPartOrder())
                .like(StringUtils.isNotBlank(query.getPickingPeople()),"picking_people",query.getPickingPeople());
        IPage<MaterialPickingDO> iPage = materialPickingService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<MaterialPickingDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取(单个)")
    @GetMapping("/{id}")
    public MaterialPickingDO getMaterialPicking(@PathVariable Long id) {
        //todo: 需要转Vo

        return materialPickingService.getById(id);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Boolean updateMaterialPicking(@RequestBody MaterialPickingDO materialPickingDO) {
        return materialPickingService.updateById(materialPickingDO);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Boolean saveMaterialPicking(@RequestBody MaterialPickingDO materialPickingDO) {
        return materialPickingService.save(materialPickingDO);
    }

    @ApiOperation(value = "删除(批量))")
    @DeleteMapping("")
    public Boolean deleteMaterialPickingByIds(@RequestBody List<Long> idList) {
        return materialPickingService.removeByIds(idList);
    }

    @ApiOperation(value = "删除(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteMaterialPickingById(@PathVariable Long id) {
        return materialPickingService.removeById(id);
    }
}
