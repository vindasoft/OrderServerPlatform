/**
 * @copyright vindasoft in version 2026-01-01
 */
 
package com.vindasoft.order.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * @Description: 程序基础配置类
 * 这个类是springboot应用程序的“基础设置中心”，相当于整个系统的基础配置办公室
 * 它负责配置一些通用的全局的应用程序设置
 *
 * @author: jwd
 * @date: 2026-01-02
 * @version V1.0
 */
@Configuration // 告诉spring: 我是配置类，你在启动的时候要读取我的配置
@MapperScan("com.vindasoft.**.mapper") // 告诉mybatis: 扫描这个包下的mapper接口
public class BaseConfig {
    /**
     * @Description: 时区配置Bean
     * @return: com.fasterxml.jackson.databind.ObjectMapper
     * @author: jwd
     * @date: 2026-01-02
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        /**
         * 1、TimeZone.getDefault()：获取当前服务器的默认时区
         * 2、timeZone(...)：告诉系统使用这个时区
         * 3、整个流程： 用户发来json数据->spring用该方法解析->jackson看到时间字段->按照配置的时区（比如北京时间）来解析时间
         *           -> 存到数据库中或者返回给前端
         */
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
        };
    }

}