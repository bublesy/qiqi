package com.qiqi.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理 - 前后端分离情况下返回json数据格式
 * @author QiQiDream
 * @since 2019/11/18 9:58
 */
@Slf4j
@Component
public class DreamAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(4001);
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            response.getWriter().println("用户名或密码不正确");
        } else if (e instanceof LockedException) {
            response.getWriter().println("账户被锁定，请联系管理员!");
        } else if (e instanceof CredentialsExpiredException) {
            response.getWriter().println("证书过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            response.getWriter().println("账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            response.getWriter().println("账户被禁用，请联系管理员!");
        } else {
            response.getWriter().println(e.getMessage());
        }
        response.getWriter().flush();
    }

}
