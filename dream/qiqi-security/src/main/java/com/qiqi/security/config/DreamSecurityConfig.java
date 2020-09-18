package com.qiqi.security.config;

import com.qiqi.security.*;
import com.qiqi.security.filter.DreamUsernamePasswordAuthenticationFilter;
import com.qiqi.security.filter.MyAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author QiQiDream
 * @date 2020-06-09 17:07
 */
@Configuration
@EnableWebSecurity
public class DreamSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private DreamAuthenticationEntryPoint dreamAuthenticationEntryPoint;
    @Resource
    protected DreamAuthenticationFailureHandler dreamAuthenticationFailureHandler;
    @Resource
    protected DreamAuthenticationSuccessHandler dreamAuthenticationSuccessHandler;
    @Resource
    private MyAuthenticationFilter myAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //http相关的配置，包括登入登出、异常处理、会话管理等
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

        // 禁用CSRF 开启跨域 及 不使用session
        http.csrf().disable().cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // OPTIONS(选项)：查找适用于一个特定网址资源的通讯选择。 在不需执行具体的涉及数据传输的动作情况下， 允许客户端来确定与资源相关的选项以及 / 或者要求， 或是一个服务器的性能
        registry.antMatchers(HttpMethod.OPTIONS,"/**").denyAll();

        // 未登录认证异常
        http.exceptionHandling().authenticationEntryPoint(dreamAuthenticationEntryPoint);

        // 登录过后访问无权限的接口时自定义响应内容
        // http.exceptionHandling().accessDeniedHandler(urlAccessDeniedHandler);

        http.addFilterAt(dreamUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(myAuthenticationFilter, BasicAuthenticationFilter.class);

        registry.antMatchers("/login").permitAll();

        // 其余所有请求都需要认证
        registry.anyRequest().authenticated();
    }

    /**
     * 忽略拦截url或静态资源文件夹
     *      web.ignoring(): 会直接过滤该url - 将不会经过Spring Security过滤器链
     *      http.permitAll(): 不会绕开spring-security验证，相当于是允许该路径通过
     */
    @Override
    public void configure(WebSecurity web) {
        //核心过滤器配置
        web.ignoring().antMatchers(
                "/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/v2/api-docs/**", "/swagger-resources/**",
                "/test"
        );
    }

    @Bean
    DreamUsernamePasswordAuthenticationFilter dreamUsernamePasswordAuthenticationFilter() throws Exception {
        DreamUsernamePasswordAuthenticationFilter filter = new DreamUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(dreamAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(dreamAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/login");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public BCryptPasswordEncoder encoding(){
        return new BCryptPasswordEncoder();
    }

}
