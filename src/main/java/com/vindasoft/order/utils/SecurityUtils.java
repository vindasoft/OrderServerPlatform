/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.utils;

import com.vindasoft.order.domain.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description: 安全服务工具类
 * @author: jwd
 * @date: 2026-01-02
 */
public class SecurityUtils {
    /**
     * 获取当前登录用户信息
     * 功能：从security的上下文中获取当前已经认证的用户信息
     *
     * @return LoginUser对象（包含了当前登录用户的完整信息）
     */
    public static LoginUser getLoginUser(){
        return (LoginUser) SecurityContextHolder.getContext() // 1、获取Security的上下文
            .getAuthentication() // 2、获取认证信息
            .getPrincipal(); // 3、获取认证主体（并强制转换为LoginUser对象）
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    public static String getUserId(){
        return getLoginUser().getUserId();
    }
}