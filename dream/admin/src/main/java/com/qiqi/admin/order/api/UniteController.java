package com.qiqi.admin.order.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.order.dto.UniteDTO;
import com.qiqi.order.entity.UniteDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.order.service.UniteService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 结合 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api("结合相关接口")
@RestController
@RequestMapping("/unite")
public class UniteController {

    @Resource
    private UniteService uniteService;

    @GetMapping("/list")
    public List<UniteDO> getList(){
        return uniteService.list();
    }

    @ApiOperation(value = "获取结合(列表)")
    @PostMapping("all")
    public PageEntity<UniteDO> getUnitePage(@RequestBody UniteDTO query) {
        QueryWrapper<UniteDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()),"name",query.getName());
        IPage<UniteDO> iPage = uniteService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<UniteDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取结合(单个)")
    @GetMapping("/{id}")
    public UniteDO getUnite(@PathVariable Long id) {
        //todo: 需要转Vo

        return uniteService.getById(id);
    }

    @ApiOperation(value = "修改结合")
    @PutMapping("")
    public Boolean updateUnite(@RequestBody UniteDO uniteDO) {
        return uniteService.updateById(uniteDO);
    }

    @ApiOperation(value = "新增结合")
    @PostMapping("")
    public Boolean saveUnite(@RequestBody UniteDO uniteDO) {
        return uniteService.save(uniteDO);
    }

    @ApiOperation(value = "删除结合(批量))")
    @DeleteMapping("")
    public Boolean deleteUniteByIds(@RequestBody List<Long> idList) {
        return uniteService.removeByIds(idList);
    }

    @ApiOperation(value = "删除结合(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteUniteById(@PathVariable Long id) {
        return uniteService.removeById(id);
    }
}
