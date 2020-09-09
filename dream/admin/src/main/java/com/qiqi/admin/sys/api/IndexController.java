package com.qiqi.admin.sys.api;

import com.qiqi.security.util.UserUtil;
import com.qiqi.sys.model.UserInfoVo;
import com.qiqi.sys.service.SysMenuService;
import com.qiqi.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author QiQiDream
 * @date 2020-06-30 14:39
 */
@Api("主页相关接口")
@RestController
@RequestMapping("/sys")
public class IndexController {
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserService sysUserService;


    @ApiOperation(value = "获取用户信息")
    @GetMapping("/user/info")
    public UserInfoVo getSysUserInfo() {
        return sysUserService.getSysUserInfo(UserUtil.getLoginUserInfo());
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public Boolean logout() {
        return true;
    }
}
