package com.qiqi.admin.order.util;

import com.qiqi.security.util.JwtTokenUtil;
import com.qiqi.sys.entity.SysUserDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hc
 * @since 2020-09-19
 */
@Component
public class UserInfoUtil {
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    public SysUserDO getUser(HttpServletRequest request){
        String token = jwtTokenUtil.getTokenFromRequest(request);
        return jwtTokenUtil.getUserInfoFromToken(token);
    }
}
