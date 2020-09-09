package com.qiqi.security.util;

import cn.hutool.core.convert.Convert;
import com.qiqi.sys.entity.SysUserDO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author QiQiDream
 * @date 2020-06-28 15:58
 */
public class UserUtil {

    public static SysUserDO getLoginUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //有登陆用户就返回登录用户，没有就返回null
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return Convert.convert(SysUserDO.class, authentication.getPrincipal());
            }
        }
        return null;
    }

    public static SysUserDO getLoginUserRole() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //有登陆用户就返回登录用户，没有就返回null
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                List<String> roleCodes = authorities.stream().map(GrantedAuthority::getAuthority).collect(toList());
                return Convert.convert(SysUserDO.class, authentication.getAuthorities());
            }
        }
        return null;
    }

}
