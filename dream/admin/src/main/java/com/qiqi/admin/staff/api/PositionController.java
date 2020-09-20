package com.qiqi.admin.staff.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiqi.admin.staff.dto.PositionDTO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.staff.entity.PositionDO;
import com.qiqi.staff.service.PositionService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 职位 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Api("职位相关接口")
@RestController
@RequestMapping("/position")
public class PositionController {

    @Resource
    private PositionService positionService;

    @ApiOperation(value = "获取职位(列表)")
    @PostMapping("all")
    public PageEntity<PositionDO> getPositionPage(@RequestBody PositionDTO query) {
        QueryWrapper<PositionDO> queryWrapper = new QueryWrapper<>();
        IPage<PositionDO> iPage = positionService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<PositionDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取职位(单个)")
    @GetMapping("/{id}")
    public PositionDO getPosition(@PathVariable Long id) {
        //todo: 需要转Vo

        return positionService.getById(id);
    }

    @ApiOperation(value = "修改职位")
    @PutMapping("")
    public Boolean updatePosition(@RequestBody PositionDO positionDO) {
        return positionService.updateById(positionDO);
    }

    @ApiOperation(value = "新增职位")
    @PostMapping("")
    public Boolean savePosition(@RequestBody PositionDO positionDO) {
        return positionService.save(positionDO);
    }

    @ApiOperation(value = "删除职位(批量))")
    @DeleteMapping("")
    public Boolean deletePositionByIds(@RequestBody List<Long> idList) {
        return positionService.removeByIds(idList);
    }

    @ApiOperation(value = "删除职位(单个))")
    @DeleteMapping("/{id}")
    public Boolean deletePositionById(@PathVariable Long id) {
        return positionService.removeById(id);
    }
}
