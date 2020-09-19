package com.qiqi.admin.order.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.order.dto.ColourDTO;
import com.qiqi.order.entity.ColourDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.order.service.ColourService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 颜色 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api(tags = "颜色相关接口")
@RestController
@RequestMapping("/colour")
public class ColourController {

    @Resource
    private ColourService colourService;

    @GetMapping("/list")
    public List<ColourDO> getList(){
        return colourService.list();
    }

    @ApiOperation(value = "获取颜色(列表)")
    @PostMapping("/all")
    public PageEntity<ColourDO> getColourPage(@RequestBody ColourDTO query) {
        QueryWrapper<ColourDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(query.getName()),"name",query.getName());
        IPage<ColourDO> iPage = colourService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<ColourDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取颜色(单个)")
    @GetMapping("/{id}")
    public ColourDO getColour(@PathVariable Long id) {
        //todo: 需要转Vo

        return colourService.getById(id);
    }

    @ApiOperation(value = "修改颜色")
    @PutMapping("")
    public Boolean updateColour(@RequestBody ColourDO colourDO) {
        return colourService.updateById(colourDO);
    }

    @ApiOperation(value = "新增颜色")
    @PostMapping("")
    public Boolean saveColour(@RequestBody ColourDO colourDO) {
        return colourService.save(colourDO);
    }

    @ApiOperation(value = "删除颜色(批量))")
    @DeleteMapping("")
    public Boolean deleteColourByIds(@RequestBody List<Long> idList) {
        return colourService.removeByIds(idList);
    }

    @ApiOperation(value = "删除颜色(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteColourById(@PathVariable Long id) {
        return colourService.removeById(id);
    }
}
