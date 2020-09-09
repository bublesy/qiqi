package com.qiqi.admin.dev.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.admin.dev.model.MenuTreeVo;
import com.qiqi.common.util.tree.TreeUtil;
import com.qiqi.sys.entity.SysMenuDO;
import com.qiqi.sys.model.MenuVo;
import com.qiqi.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-06-30
 */
@Api("系统菜单相关接口")
@RestController
@RequestMapping("/dev/menu")
public class DevMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单树形数据")
    @GetMapping("/tree")
    public List<MenuTreeVo> getMenuTreeSelect() {
        List<SysMenuDO> menus = sysMenuService.list(new QueryWrapper<SysMenuDO>().orderByAsc("type","sort"));
        List<MenuTreeVo> convert = Convert.convert(new TypeReference<List<MenuTreeVo>>() {}, menus);
        return TreeUtil.getTreeList(0L, convert);
    }

    @ApiOperation(value = "获取系统菜单(列表)")
    @GetMapping("")
    public List<MenuVo> getSysMenuPage() {
        List<SysMenuDO> menuList = sysMenuService.list(new QueryWrapper<SysMenuDO>().orderByAsc("type","sort"));
        List<MenuVo> convert = Convert.convert(new TypeReference<List<MenuVo>>() {}, menuList);
        return TreeUtil.getTreeList(0L, convert);
    }

    @ApiOperation(value = "获取系统菜单(单个)")
    @GetMapping("/{id}")
    public SysMenuDO getSysMenu(@PathVariable Long id) {
        //todo: 需要转Vo

        return sysMenuService.getById(id);
    }

    @ApiOperation(value = "修改系统菜单")
    @PutMapping("")
    public Boolean updateSysMenu(@RequestBody SysMenuDO sysMenuDO) {
        return sysMenuService.updateById(sysMenuDO);
    }

    @ApiOperation(value = "新增系统菜单")
    @PostMapping("")
    public Boolean saveSysMenu(@RequestBody SysMenuDO sysMenuDO) {
        return sysMenuService.save(sysMenuDO);
    }

    @ApiOperation(value = "删除系统菜单(批量))")
    @DeleteMapping("")
    public Boolean deleteSysMenuByIds(@RequestBody List<Long> idList) {
        return sysMenuService.removeByIds(idList);
    }

    @ApiOperation(value = "删除系统菜单(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteSysMenuById(@PathVariable Long id) {
        return sysMenuService.removeById(id);
    }
}
