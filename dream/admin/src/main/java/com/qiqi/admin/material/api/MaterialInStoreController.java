package com.qiqi.admin.material.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.admin.material.dto.MaterialDataDTO;
import com.qiqi.admin.material.dto.MaterialInStoreDTO;
import com.qiqi.material.entity.MaterialInStoreDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.material.service.MaterialInStoreService;

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
@Api(tags = "辅料入库相关接口")
@RestController
@RequestMapping("/material/in/store")
public class MaterialInStoreController {

    @Resource
    private MaterialInStoreService materialInStoreService;

    @ApiOperation(value = "获取(列表)")
    @PostMapping("/list")
    public PageEntity<MaterialInStoreDO> getMaterialInStorePage(@RequestBody MaterialInStoreDTO query) {
        QueryWrapper<MaterialInStoreDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!ObjectUtils.isEmpty(query.getDate()),"date",query.getDate())
                .like(StringUtils.isNotBlank(query.getSupplier()),"supplier",query.getSupplier())
                .like(StringUtils.isNotBlank(query.getPartOrder()),"part_order",query.getPartOrder())
                .like(StringUtils.isNotBlank(query.getNo()),"no",query.getNo());
        IPage<MaterialInStoreDO> iPage = materialInStoreService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<MaterialInStoreDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取(单个)")
    @GetMapping("/{id}")
    public MaterialInStoreDO getMaterialInStore(@PathVariable Long id) {
        //todo: 需要转Vo

        return materialInStoreService.getById(id);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Boolean updateMaterialInStore(@RequestBody MaterialInStoreDO materialInStoreDO) {
        return materialInStoreService.updateById(materialInStoreDO);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Boolean saveMaterialInStore(@RequestBody MaterialInStoreDO materialInStoreDO) {
        return materialInStoreService.save(materialInStoreDO);
    }

    @ApiOperation(value = "删除(批量))")
    @DeleteMapping("")
    public Boolean deleteMaterialInStoreByIds(@RequestBody List<Long> idList) {
        return materialInStoreService.removeByIds(idList);
    }

    @ApiOperation(value = "删除(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteMaterialInStoreById(@PathVariable Long id) {
        return materialInStoreService.removeById(id);
    }
}
