/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profile/**")
                .addResourceLocations("file"+ServiceConfig.getProfile()+"/");
    }
}
