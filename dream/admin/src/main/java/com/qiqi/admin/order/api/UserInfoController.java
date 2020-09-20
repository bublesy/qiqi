package com.qiqi.admin.order.api;

import com.qiqi.admin.order.util.UserInfoUtil;
import com.qiqi.sys.entity.SysUserDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hc
 * @since 2020-09-19
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoUtil userInfoUtil;

    @GetMapping("")
    public SysUserDO getUser(HttpServletRequest request){
        SysUserDO user = userInfoUtil.getUser(request);
        return user;
    }
}
