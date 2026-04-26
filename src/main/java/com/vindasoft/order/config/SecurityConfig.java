/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.config;

import com.vindasoft.order.configure.JwtAuthEntryPoint;
import com.vindasoft.order.configure.JwtAuthTokenFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description: spring security安全配置类
 * 1、哪些请求可以自由出入（公开接口）
 * 2、哪些请求需要检查权限（需要登录）
 * 3、如何检查权限（JWT认证）
 * 4、发现可疑的请求如何处理（异常处理）
 *
 * @author: jwd
 * @date: 2026-01-02
 */
@EnableMethodSecurity(securedEnabled = true) // 启用方法级别的安全控制（就像给每个房间再加一把锁）
@Configuration
public class SecurityConfig {
    @Resource
    private JwtAuthEntryPoint authEntryPoint;

    @Resource
    private JwtAuthTokenFilter authTokenFilter;

    /**
     * 安全过滤器链
     * 1、跨域检查 -> 2.禁用CSRF防护 -> 3.异常处理 -> 4.路径的权限检查 -> 4.JWT认证
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1、开启跨域支持（允许前端应用访问）
            .cors(Customizer.withDefaults())

            // 2、禁用csrf（跨站请求伪造）防护: 否则每次请求都有弹出spring security 的登录页面（输入用户名和密码）
            // （我们是前后端分离+JWT认证方式，可以关闭，因为每次请求都携带了token）
            .csrf(AbstractHttpConfigurer::disable)

            // 3、响应头配置：防止点击劫持攻击
            // SAMEORIGIN: 只允许同源页面嵌入; DENY: 禁止所有嵌入
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            )

            // 4、禁用session（使用无状态认证(JWT),因为每次请求都独立验证，不需要记住用户的登录状态）
            // 好处：容易拓展，多个服务器集群时，用户登录状态不会因服务器集群的切换而丢失
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 5、异常处理：当认证失败的处理方式
            // 比如：没有带token，token过期，token无效
            .exceptionHandling(e -> e.authenticationEntryPoint(authEntryPoint))

            // 6、路径权限检查：指定哪些路径需要登录认证，哪些路径不需要登录认证
            .authorizeHttpRequests(req ->
                    // 公共接口：所有人都可以访问
                    req.requestMatchers("/login","/register", "/profile/**").permitAll()
                    // 其他所有请求都需要认证
                    .anyRequest().authenticated()
            )

            // 7、退出登录处理
            .logout(logout -> logout.logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, auth) -> resp.setStatus(200))
            )

            // 8、添加JWT的过滤器: 在用户的过滤器之前执行
            .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
