package com.qiqi.security.filter;

import cn.hutool.core.util.StrUtil;
import com.qiqi.security.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问鉴权 - 每次访问接口都会经过此
 *
 * @author QiQiDream
 * @date 2019/11/18 9:56
 */
@Component
public class MyAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenUtil.getTokenFromRequest(request);
        if (StrUtil.isNotBlank(token)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    jwtTokenUtil.getUserInfoFromToken(token),
                    null,
                    jwtTokenUtil.getUserRoleFromToken(token));

            // 全局注入角色权限信息和登录用户基本信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
