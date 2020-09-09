package com.qiqi.security.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import com.qiqi.common.constant.StaticConstants;
import com.qiqi.sys.entity.SysUserDO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JwtToken生成的工具类
 *
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 *
 * @author QiQiDream
 * @date 2020-06-28 15:58
 */
@Slf4j
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USER_INFO = "userInfo";
    private static final String CLAIM_KEY_USER_AUTHORITIES = "authorities";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 根据负责生成JWT的token
     */
    private String generateToken( Long id, String subject, Map<String, Object> claims) {
        String builder = Jwts.builder()
                .setHeaderParam("type", StaticConstants.TOKEN_TYPE)
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return StaticConstants.TOKEN_PREFIX + builder;
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String authHeader = request.getHeader(StaticConstants.TOKEN_HEADER);

        if (authHeader != null && authHeader.startsWith(StaticConstants.TOKEN_PREFIX)) {
            return authHeader.substring(StaticConstants.TOKEN_PREFIX.length());
        }
        return null;
    }


    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取登录用户信息
     */
    public SysUserDO getUserInfoFromToken(String token) {
        SysUserDO userInfo;
        try {
            Claims claims = getClaimsFromToken(token);
            userInfo = Convert.convert(SysUserDO.class, claims.get(CLAIM_KEY_USER_INFO));
        } catch (Exception e) {
            userInfo = null;
        }
        return userInfo;
    }

    /**
     * 从token中获取登录用户角色
     */
    public Collection<? extends GrantedAuthority> getUserRoleFromToken(String token) {
        Collection<? extends GrantedAuthority> userRole;
        try {
            Claims claims = getClaimsFromToken(token);
            List<String> roleList = Convert.convert(new TypeReference<List<String>>() {}, claims.get(CLAIM_KEY_USER_AUTHORITIES));
            userRole = roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        } catch (Exception e) {
            userRole = null;
        }
        return userRole;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(SysUserDO sysUserDO, List<String> roleCodes) {

        Map<String, Object> claims = new HashMap<>(4);
        claims.put(CLAIM_KEY_USER_INFO, sysUserDO);
        claims.put(CLAIM_KEY_USER_AUTHORITIES, roleCodes);
        return generateToken(sysUserDO.getId(),sysUserDO.getUsername(),claims);
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param oldToken 带tokenHead的token
     */
    public String refreshHeadToken(String oldToken) {
        if(StrUtil.isEmpty(oldToken)){
            return null;
        }
        String token = oldToken.substring(StaticConstants.TOKEN_PREFIX.length());
        if(StrUtil.isEmpty(token)){
            return null;
        }
        //token校验不通过
        Claims claims = getClaimsFromToken(token);
        if(claims==null){
            return null;
        }
        //如果token已经过期，不支持刷新
        if(isTokenExpired(token)){
            return null;
        }
        //如果token在30分钟之内刚刷新过，返回原token
        if(tokenRefreshJustBefore(token)){
            return token;
        }else{
            return generateToken(Long.parseLong(claims.getId()),claims.getSubject(),claims);
        }
    }

    /**
     * 判断token在30分钟内是否刚刚刷新过
     * @param token 原token
     *
     */
    private boolean tokenRefreshJustBefore(String token) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.getIssuedAt();
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        return refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, 1800));
    }
}
