/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @Description: 首页控制器，处理根路径请求
 * @author: lixiaoqiang
 * @date: 2026-01-02
 */
@RestController //标记为REST API控制器（它会自动把返回值转换为json格式）
@RequestMapping("/index") // 查询访问的路径
public class IndexController {
    @GetMapping
    public String home() {
        LocalDateTime localDateTime = LocalDateTime.now();

        String curStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "Hello World!"+curStr;
    }
}