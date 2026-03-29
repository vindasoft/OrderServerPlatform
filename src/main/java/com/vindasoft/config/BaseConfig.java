/**
 * @copyright vindasoft in version 2026-01-01
 */
 
package com.vindasoft.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * @ClassName: BaseConfig
 * @Description: 程序基础配置类，整个springboot基础应用程序的基础设置中心
 * @author: lixiaoqiang
 * @date: 2026-01-02
 * @version V1.0
 */
@Configuration // 告诉spring: 我是配置类，你在启动的时候要读取我的配置
@MapperScan("com.vindasoft.**.mapper") // 告诉mybatis: 扫描这个包下的mapper接口
public class BaseConfig {
    /**
     * @Description: 时区配置Bean
     * @return: com.fasterxml.jackson.databind.ObjectMapper
     * @author: lixiaoqiang
     * @date: 2026-01-02
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
        };
    }

}
