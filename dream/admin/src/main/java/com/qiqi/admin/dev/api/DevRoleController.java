package com.qiqi.admin.dev.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.admin.dev.model.DevRoleVO;
import com.qiqi.common.base.BaseModel;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.common.entity.SelectOption;
import com.qiqi.sys.entity.SysRoleDO;
import com.qiqi.sys.entity.SysRoleMenuDO;
import com.qiqi.sys.service.SysRoleMenuService;
import com.qiqi.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
@RequestMapping("/dev/role")
public class DevRoleController {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @ApiOperation(value = "获取角色选择器数据")
    @GetMapping("/selector")
    public List<SelectOption> getSysRoleSelector() {
        List<SysRoleDO> list = sysRoleService.list(new LambdaQueryWrapper<SysRoleDO>().select(BaseModel::getId,SysRoleDO::getName));

        return Convert.convert(new TypeReference<List<SelectOption>>() {}, list);
    }

    @ApiOperation(value = "获取系统角色(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<DevRoleVO> getSysRolePage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                @RequestParam(value = "count",defaultValue = "10") Long count,
                                                @RequestParam(value = "name",defaultValue = "") String name) {
        IPage<SysRoleDO> iPage = sysRoleService.page(new Page<>(page,count));
        List<DevRoleVO> convert = Convert.convert(new TypeReference<List<DevRoleVO>>() {}, iPage.getRecords());
        List<SysRoleMenuDO> roleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenuDO>()
                .in(SysRoleMenuDO::getRoleId, convert.stream().map(DevRoleVO::getId).collect(toList())));
        for (DevRoleVO roleVO : convert) {
            roleVO.setMenuIds(roleMenuList.stream().filter(item -> item.getRoleId().equals(roleVO.getId())).map(SysRoleMenuDO::getMenuId).collect(toList()));
        }
        return new PageEntity<>(iPage.getTotal(),convert);
    }

    @ApiOperation(value = "获取系统角色(单个)")
    @GetMapping("/{id}")
    public DevRoleVO getSysRole(@PathVariable Long id) {
        DevRoleVO convert = Convert.convert(DevRoleVO.class, sysRoleService.getById(id));
        convert.setMenuIds(sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenuDO>()
                .eq(SysRoleMenuDO::getRoleId, id)).stream().map(SysRoleMenuDO::getMenuId).collect(toList()));
        return convert;
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

    @ApiOperation(value = "编辑角色菜单")
    @PutMapping("/{id}/menu")
    public Boolean editRoleMenus(@RequestBody List<Long> menuIds, @PathVariable Long id) {

        List<SysRoleMenuDO> roleMenuList = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenuDO sysRoleMenuDO = new SysRoleMenuDO();
            sysRoleMenuDO.setRoleId(id);
            sysRoleMenuDO.setMenuId(menuId);
            roleMenuList.add(sysRoleMenuDO);
        }

        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenuDO>().eq(SysRoleMenuDO::getRoleId,id));
        return sysRoleMenuService.saveBatch(roleMenuList);
    }
}
