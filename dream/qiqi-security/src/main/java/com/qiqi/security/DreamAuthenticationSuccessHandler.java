package com.qiqi.security;

import com.qiqi.security.util.JwtTokenUtil;
import com.qiqi.sys.entity.SysUserDO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 认证成功处理
 * @author QiQiDream
 * @since 2019/11/18 9:58
 */
@Component
public class DreamAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication auth) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());


        SysUserDO sysUserDO = ((SysUserDO) auth.getPrincipal());
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roleCodes = authorities.stream().map(GrantedAuthority::getAuthority).collect(toList());
        String token = jwtTokenUtil.generateToken(sysUserDO,roleCodes);

        response.getWriter().println(token);
        response.getWriter().flush();
    }

}
