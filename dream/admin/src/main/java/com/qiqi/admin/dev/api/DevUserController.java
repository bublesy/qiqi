package com.qiqi.admin.dev.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.admin.dev.model.DevUserVO;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.sys.entity.SysRoleDO;
import com.qiqi.sys.entity.SysUserDO;
import com.qiqi.sys.entity.SysUserRoleDO;
import com.qiqi.sys.service.SysRoleService;
import com.qiqi.sys.service.SysUserRoleService;
import com.qiqi.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
@RequestMapping("/dev/user")
public class DevUserController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取系统用户(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<DevUserVO> getSysUserPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<SysUserDO> iPage = sysUserService.page(new Page<>(page,count));

        List<DevUserVO> convert = Convert.convert(new TypeReference<List<DevUserVO>>() {}, iPage.getRecords());

        List<SysUserRoleDO> userRoleList = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRoleDO>()
                .in(SysUserRoleDO::getUserId, convert.stream().map(DevUserVO::getId).collect(toList())));
        List<SysRoleDO> roleList = sysRoleService.list(new LambdaQueryWrapper<SysRoleDO>()
                .select(SysRoleDO::getId,SysRoleDO::getName)
                .in(SysRoleDO::getId, userRoleList.stream().map(SysUserRoleDO::getRoleId).collect(toList())));
        for (DevUserVO userVO : convert) {
            userVO.setRoleIds(userRoleList.stream().filter(item -> item.getUserId().equals(userVO.getId())).map(SysUserRoleDO::getRoleId).collect(toList()));
            List<String> roleNames = roleList.stream().filter(item -> userVO.getRoleIds().indexOf(item.getId()) != -1).map(SysRoleDO::getName).collect(toList());
            userVO.setRoleNames(roleNames);
        }

        return new PageEntity<>(iPage.getTotal(),convert);
    }

    @ApiOperation(value = "获取系统用户(单个)")
    @GetMapping("/{id}")
    public SysUserDO getSysUser(@PathVariable Long id) {
        //todo: 需要转Vo

        return sysUserService.getById(id);
    }

    @ApiOperation(value = "修改系统用户")
    @PutMapping("")
    public Boolean updateSysUser(@RequestBody DevUserVO devUserVO) {
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(devUserVO,sysUserDO);
        sysUserService.updateById(sysUserDO);
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRoleDO>().eq(SysUserRoleDO::getUserId, sysUserDO.getId()));
        List<SysUserRoleDO> userRoleDOList = new ArrayList<>();
        for (Long roleId : devUserVO.getRoleIds()) {
            SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
            sysUserRoleDO.setRoleId(roleId);
            sysUserRoleDO.setUserId(sysUserDO.getId());
            userRoleDOList.add(sysUserRoleDO);
        }
        return sysUserRoleService.saveBatch(userRoleDOList);
    }

    @ApiOperation(value = "新增系统用户")
    @PostMapping("")
    public Boolean saveSysUser(@RequestBody DevUserVO devUserVO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<SysUserRoleDO> userRoleDOList = new ArrayList<>();
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(devUserVO,sysUserDO);
        sysUserDO.setPassword(passwordEncoder.encode("123456"));
        sysUserService.save(sysUserDO);
        for (Long roleId : devUserVO.getRoleIds()) {
            SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
            sysUserRoleDO.setRoleId(roleId);
            sysUserRoleDO.setUserId(sysUserDO.getId());
            userRoleDOList.add(sysUserRoleDO);
        }
        return sysUserRoleService.saveBatch(userRoleDOList);
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
