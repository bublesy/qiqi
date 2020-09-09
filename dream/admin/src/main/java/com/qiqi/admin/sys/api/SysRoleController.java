package com.qiqi.admin.sys.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.sys.entity.SysRoleDO;
import com.qiqi.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-06-30
 */
@Api("系统角色相关接口")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取系统角色(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<SysRoleDO> getSysRolePage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<SysRoleDO> iPage = sysRoleService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<SysRoleDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取系统角色(单个)")
    @GetMapping("/{id}")
    public SysRoleDO getSysRole(@PathVariable Long id) {
        //todo: 需要转Vo

        return sysRoleService.getById(id);
    }

    @ApiOperation(value = "修改系统角色")
    @PutMapping("")
    public Boolean updateSysRole(@RequestBody SysRoleDO sysRoleDO) {
        return sysRoleService.updateById(sysRoleDO);
    }

    @ApiOperation(value = "新增系统角色")
    @PostMapping("")
    public Boolean saveSysRole(@RequestBody SysRoleDO sysRoleDO) {

        return sysRoleService.save(sysRoleDO);
    }

    @ApiOperation(value = "删除系统角色(批量))")
    @DeleteMapping("")
    public Boolean deleteSysRoleByIds(@RequestBody List<Long> idList) {

        return sysRoleService.removeByIds(idList);
    }

    @ApiOperation(value = "删除系统角色(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteSysRoleById(@PathVariable Long id) {

        return sysRoleService.removeById(id);
    }
}
