package com.qiqi.admin.sys.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.sys.entity.SysMenuDO;
import com.qiqi.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取系统菜单(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<SysMenuDO> getSysMenuPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<SysMenuDO> iPage = sysMenuService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<SysMenuDO>>() {}, iPage.getRecords()));
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
