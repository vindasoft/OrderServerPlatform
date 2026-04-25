/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * @Description: 资源处理配置类
 * 这个类主要负责两件事情：
 * 1、配置静态资源访问路径（比如用户上传的图片、文件等等）
 * 2、配置跨域访问规则（允许前端应用访问后端API）
 *
 * 可以把这个类看作是系统的“接待处”和“通行证办理处”
 * @author: jwd
 * @date: 2026-01-02
 */
@Configuration // 告诉spring: 我是配置类，你在启动的时候要读取我的配置
public class ResourcesConfig implements WebMvcConfigurer {
    /**
     * 配置静态资源处理器
     *
     * 这个方法负责告诉spring:当用户请求某些路径时，应该去服务器的哪个文件夹里找到对应的文件
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profile/**")
                .addResourceLocations("file"+ServiceConfig.getProfile()+"/");
    }

    /**
     * 跨域配置源：配置允许访问我的网站
     *
     * 这个方法负责告诉spring: 当用户请求某些路径时，允许跨域访问
     * 也就是配置了哪些“外部的”网站可以访问我们的后端API
     * 跨域就像是“跨省通信证”
     * 默认情况下，浏览器不允许用户访问其他网站的API，但允许用户访问本机的API
     */
    @Bean  // 告诉spring: 这个方法返回的对象要放到容器里进行管理
    public CorsConfigurationSource corsConfigurationSource( ) {
        // 创建一个跨域配置对象
        CorsConfiguration config = new CorsConfiguration();

        // 1、允许哪些来源可以访问(*表示允许所有来源)
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        // source.setAllowedOriginPatterns(Collections.singletonList("http://localhost:9090"));

        /**
         * 2、允许携带哪些请求头可以访问（*表示允许所有请求头）：证件
         * Host：目标域名 + 端口，标识请求访问的服务器主机，必带
         * User-Agent: 客户端身份标识（浏览器 / 设备 / 系统 / 爬虫信息）
         * Content-Type：请求体数据格式，POST/PUT 必带
         *    常用值：application/json（JSON 接口）、 application/x-www-form-urlencoded（普通表单）
         *    multipart/form-data（文件上传）、 text/plain（纯文本）
         * Accept：客户端能接收的响应数据类型，例如Accept: application/json, text/html
         * Cookie：本地缓存的会话、用户标识、登录态，浏览器自动携带
         * Authorization：接口鉴权核心头
         * Origin：跨域请求时携带，标识请求源域名，服务端用来校验跨域
         * Referer：来路页面，告诉服务器「我从哪个页面跳转过来」，常用于防盗链、流量统计
         * Accept-Encoding：支持的压缩格式：gzip、deflate、br，用来压缩响应体积
         * 自定义请求头： 例如 X-Token（接口令牌） / Sign（接口签名） ：用来防篡改
         */
        config.setAllowedHeaders(Collections.singletonList("*"));

        // 3、允许哪些方法可以访问（GET、POST、PUT、DELETE）：*表示允许所有方法
        config.setAllowedMethods(Collections.singletonList("*"));

        // 4、是否允许发送凭证(比如：cookies、http认证信息)：true表示允许，false表示不允许
        // 比如 设置为true：表示允许前端在请求时携带cookie等信息，就像允许访客携带自己的会员卡
        config.setAllowCredentials(true);

        /**
         * 5、设置预检请求的缓存时间：单位秒
         * 浏览器在发送复杂的请求前，会先发送一个“预检请求”来检查是否允许访问，如果允许，则发送真正的请求
         * 这个设置就是告诉浏览器：1800秒内，不需要再发送预检请求
         * 就像是办理了长期通行证：这个设置是为了提高性能，减少预检请求的次数
         */
        config.setMaxAge(1800L);

        // 6、创建基于URL的跨域配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 7、注册配置：对所有路径(/**) 都使用上面的跨域规则
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
