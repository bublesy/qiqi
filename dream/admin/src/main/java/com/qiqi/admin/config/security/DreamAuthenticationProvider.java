package com.qiqi.admin.config.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiqi.sys.entity.SysUserDO;
import com.qiqi.sys.service.SysRoleService;
import com.qiqi.sys.service.SysUserRoleService;
import com.qiqi.sys.service.SysUserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 自定义认证处理
 * @author QiQiDream
 * @date 2019/11/18 9:58
 */
@Component
public class DreamAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取UsernamePasswordAuthenticationFilter返回的用户名、密码
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        SysUserDO sysUserDO = sysUserService.getOne(new LambdaQueryWrapper<SysUserDO>().eq(SysUserDO::getUsername, userName));

        //验证用户名是否正确
        if (ObjectUtils.isEmpty(sysUserDO)){
            throw new UsernameNotFoundException("用户名不存在！");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isValid = passwordEncoder.matches(password, sysUserDO.getPassword());

        // 验证密码
        if (!isValid) {
            throw new BadCredentialsException("密码错误！");
        }

        Collection<? extends GrantedAuthority> authorities = sysRoleService.getRoleCodeByUserId(sysUserDO.getId())
                .stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(sysUserDO, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
