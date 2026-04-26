/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Description: 登录用户身份类
 * 作用：封装登录用户信息
 * 相当于系统内部的“用户身份证”
 * @author: jwd
 * @date: 2026-01-02
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 登录时间：用户登录时间戳（秒）
     */
    private Long loginTime;

    /**
     * 过期时间：token的过期时间戳
     */
    private Long expireTime;

    /**
     * 用户信息：用户实体类
     */
    private UserInfo userInfo;

    /**
     * 带参数的构造方法
     *
     * @param userId
     * @param userInfo
     */
    public LoginUser(String userId, UserInfo userInfo) {
        this.userId = userId;
        this.userInfo = userInfo;
    }

    /**
     * 获取用户的权限集合：这个方法必须重写
     *
     * @return null
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 获取用户名：这个方法必须重写
     *
     * @return 空
     */
    @Override
    public String getUsername() {
        return "";
    }

    /**
     * 获取密码：这个方法必须重写
     *
     * @return 空
     */
    @Override
    public String getPassword() {
        return "";
    }
}