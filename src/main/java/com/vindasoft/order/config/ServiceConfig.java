/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 读取项目相关配置
 * 这个类是一个“配置读取器”，专门负责读取application.yml配置文件中的自定义配置项，
 * 可用把这个类看作系统的“配置文件翻译官”
 *
 * @author: jwd
 * @date: 2026-01-02
 */
@Component //告诉spring: 我是一个组件，请把我放到容器里管理，在启动的时候要创建这个对象
@ConfigurationProperties(prefix = "vindasoft") //核心注解：告诉spring：请读取项目配置文件中以vindasoft开头的配置项
public class ServiceConfig {
    /**
     * 文件上传路径配置
     *
     * 这个变量对应项目配置文件中的vindasoft.profile属性
     * 注意：这个变量是静态的，所有实例对象都可以访问，比如 ServiceConfig.profile
     */
    private static String profile;

    public static String getProfile() {
        return profile;
    }

    /**
     * 设置文件上传路径
     * spring 会自动把配置文件中的属性值注入到这个变量中
     * 过程：
     * 1.spring 启动时，读取配置文件
     * 2.找到vidnasoft.profile = ./file
     * 3.创建ServiceConfig对象
     * 4.调用setProfile("./file")方法，把属性值注入到这个变量中
     *
     * @param profile 从配置文件中读取到的上传路径
     */
    public static void setProfile(String profile) {
        ServiceConfig.profile = profile;
    }
}