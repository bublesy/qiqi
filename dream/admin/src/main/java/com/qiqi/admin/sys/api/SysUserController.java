package com.qiqi.admin.sys.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.sys.entity.SysUserDO;
import com.qiqi.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-07-01
 */
@Api("系统用户相关接口")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value = "获取系统用户(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<SysUserDO> getSysUserPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<SysUserDO> iPage = sysUserService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<SysUserDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取业务员")
    @GetMapping("/salesman")
    public List<SysUserDO> getSalesman(){
        return sysUserService.list();
    }

    @ApiOperation(value = "获取系统用户(单个)")
    @GetMapping("/{id}")
    public SysUserDO getSysUser(@PathVariable Long id) {
        //todo: 需要转Vo

        return sysUserService.getById(id);
    }

    @ApiOperation(value = "修改系统用户")
    @PutMapping("")
    public Boolean updateSysUser(@RequestBody SysUserDO sysUserDO) {
        return sysUserService.updateById(sysUserDO);
    }

    @ApiOperation(value = "新增系统用户")
    @PostMapping("")
    public Boolean saveSysUser(@RequestBody SysUserDO sysUserDO) {
        return sysUserService.save(sysUserDO);
    }

    @ApiOperation(value = "删除系统用户(批量))")
    @DeleteMapping("")
    public Boolean deleteSysUserByIds(@RequestBody List<Long> idList) {
        return sysUserService.removeByIds(idList);
    }

    @ApiOperation(value = "删除系统用户(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteSysUserById(@PathVariable Long id) {
        return sysUserService.removeById(id);
    }
}
