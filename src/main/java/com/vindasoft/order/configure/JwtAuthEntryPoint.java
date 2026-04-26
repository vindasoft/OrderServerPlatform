/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vindasoft.order.domain.response.CommonResult;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description: JWT认证处理类
 * 作用：JWT令牌验证，当用户访问受保护的接口，但是未认证时，同意返回json格式的错误信息
 * @author: jwd
 * @date: 2026-01-02
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    /**
     * JSON处理器：用于对象与json字符串相互转换
     */
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 认证失败处理方法
     * 当访问受保护的接口，但是未认证时，我们的Security会自动调用这个方法，统一返回json格式的错误信息
     *
     * @param request       请求对象
     * @param response      响应对象
     * @param authException 认证异常对象
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        // 1、设置http状态码为401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 2、设置响应内容为json格式
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 3、设置字符编码为UTF-8
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        // 4、构建统一的错误响应
        String jsonResponse = objectMapper.writeValueAsString(CommonResult.error(
            String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), "登录已过期，请重新登录"));

        // 5、将错误信息写入响应对象中（将json响应数据写入http响应体）
        // response.getWriter().write(jsonResponse);
        response.getWriter().println(jsonResponse);
    }
}