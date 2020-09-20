package com.qiqi.admin.staff.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.admin.staff.dto.DeptDTO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.staff.entity.DeptDO;
import com.qiqi.staff.service.DeptService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Api("部门管理相关接口")
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    @ApiOperation(value = "获取部门管理(列表)")
    @PostMapping("/all")
    public PageEntity<DeptDO> getDeptPage(@RequestBody DeptDTO query) {

        QueryWrapper<DeptDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(query.getName()),"name",query.getName())
                .eq(!ObjectUtils.isEmpty(query.getLastModifyTime()),"last_modify_time",query.getLastModifyTime());
        IPage<DeptDO> iPage = deptService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<DeptDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取部门管理(单个)")
    @GetMapping("/{id}")
    public DeptDO getDept(@PathVariable Long id) {
        //todo: 需要转Vo

        return deptService.getById(id);
    }

    @ApiOperation(value = "修改部门管理")
    @PutMapping("")
    public Boolean updateDept(@RequestBody DeptDO deptDO) {
        return deptService.updateById(deptDO);
    }

    @ApiOperation(value = "新增部门管理")
    @PostMapping("")
    public Boolean saveDept(@RequestBody DeptDO deptDO) {
        return deptService.saveOrUpdate(deptDO);
    }

    @ApiOperation(value = "删除部门管理(批量))")
    @DeleteMapping("")
    public Boolean deleteDeptByIds(@RequestBody List<Long> idList) {
        return deptService.removeByIds(idList);
    }

    @ApiOperation(value = "删除部门管理(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteDeptById(@PathVariable Long id) {
        return deptService.removeById(id);
    }
}
