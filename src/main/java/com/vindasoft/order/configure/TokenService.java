/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vindasoft.order.domain.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description: token 验证处理类 作用：JWT令牌的生成、解析、验证 相当于系统的“身份证制作和检查站”
 * @author: jwd
 * @date: 2026-01-02
 */
@Component
@Slf4j
public class TokenService {
    /**
     * 令牌自定义标识： http请求头中的携带的token字段名
     */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌密钥：用于加密、解密token的字符串
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${token.expireTime}")
    private int expireTime;

    /**
     * JWT签名密钥：将secret配置值转换为key对象，用于签名
     */
    private Key secretKey;

    /**
     * JSON处理器：用于对象与json字符串相互转换
     */
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 系统启动时初始化密钥
     */
    @PostConstruct // 在bean初始化之后执行(即在Bean创建完之后，依赖注入完成后执行)
    public void init() {
        // 将配置的令牌密钥字符串转换为JWT签名密钥
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 创建令牌 功能：根据用户的信息生成JWT+token 使用场景：用户登录成功之后调用，生成token返回给前端
     *
     * @param loginUser 用户信息
     */
    public String createToken(LoginUser loginUser) {
        // 1、获取当前时间戳
        long now = System.currentTimeMillis();

        // 2、计算令牌有效期时间戳（将分钟转换为毫秒）
        long expirationTime = now + TimeUnit.MINUTES.toMillis(expireTime);

        // 3、更新用户信息中的时间字段
        loginUser.setLoginTime(now);
        loginUser.setExpireTime(expirationTime);

        // 4、准备JWT的声明数据
        HashMap<String, Object> claims = new HashMap<>();

        // 5、将LoginUser对象转换为json字符串
        try {
            claims.put("user_key", objectMapper.writeValueAsString(loginUser));
        } catch (Exception e) {
            throw new RuntimeException("序列化用户信息失败", e);
        }

        // 6、构建JWT token
        return Jwts.builder().setClaims(claims) // 设置声明数据
            .setExpiration(new Date(expirationTime)) // 设置令牌有效期
            .signWith(secretKey) // 设置签名密钥
            .compact(); // 生成字符串
    }

    /**
     * 获取用户身份信息 功能：从http请求中提取token，解析出用户信息 使用场景：拦截器中调用，验证用户是否登录
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 1、从http请求头中获取token
        String token = getToken(request);

        // 2、判断token是否为空或者空白
        if (StringUtils.isBlank(token)) {
            // 用户未登录
            log.error("用户未登录");
            return null;
        }

        try {
            // 3、解析token，获取用户信息
            Claims claims = parseToken(token);

            // 4、从声明数据中获取用户信息json串
            String userJson = claims.get("user_key", String.class);

            // 5、将用户信息json串反序列化，转换为LoginUser对象并返回
            return objectMapper.readValue(userJson, LoginUser.class);
        } catch (Exception e) {
            // 6、解析用户信息失败
            log.error("解析用户信息失败", e);
        }
        return null;
    }

    /**
     * 从http请求中获取token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }

    /**
     * 解析字符串，获取声明数据
     *
     * @param token
     * @return
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey) // 设置签名密钥
            .build().parseClaimsJws(token) // 解析token
            .getBody(); // 获取声明数据
    }

    /**
     * 验证token有效期
     *
     * @param loginUser
     */
    public void verifyToken(LoginUser loginUser) {
        // 获取过期时间
        long expireTime = loginUser.getExpireTime();

        // 获取当前时间戳
        long nowTime = System.currentTimeMillis();

        // 判断当前时间戳是否超过过期时间
        if (expireTime <= nowTime) {
            throw new RuntimeException("Token已过期");
        }
    }
}