package com.qiqi.admin.material.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.admin.material.dto.MaterialDataDTO;
import com.qiqi.material.entity.MaterialDataDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.material.service.MaterialDataService;

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
@Api(tags = "辅料资料相关接口")
@RestController
@RequestMapping("/material/data")
public class MaterialDataController {

    @Resource
    private MaterialDataService materialDataService;

    @ApiOperation(value = "获取(列表)")
    @PostMapping("/list")
    public PageEntity<MaterialDataDO> getMaterialDataPage(@RequestBody MaterialDataDTO query) {
        QueryWrapper<MaterialDataDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(query.getSpecification()),"specification",query.getSpecification())
                .like(StringUtils.isNotBlank(query.getUnit()),"unit",query.getUnit());
        IPage<MaterialDataDO> iPage = materialDataService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<MaterialDataDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取(单个)")
    @GetMapping("/{id}")
    public MaterialDataDO getMaterialData(@PathVariable Long id) {
        //todo: 需要转Vo

        return materialDataService.getById(id);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Boolean updateMaterialData(@RequestBody MaterialDataDO materialDataDO) {
        return materialDataService.updateById(materialDataDO);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Boolean saveMaterialData(@RequestBody MaterialDataDO materialDataDO) {
        return materialDataService.save(materialDataDO);
    }

    @ApiOperation(value = "删除(批量))")
    @DeleteMapping("")
    public Boolean deleteMaterialDataByIds(@RequestBody List<Long> idList) {
        return materialDataService.removeByIds(idList);
    }

    @ApiOperation(value = "删除(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteMaterialDataById(@PathVariable Long id) {
        return materialDataService.removeById(id);
    }
}
