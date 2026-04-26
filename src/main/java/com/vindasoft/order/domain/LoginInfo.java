/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.domain;

import lombok.Data;

/**
 * @Description: 系统登录信息
 * @author: jwd
 * @date: 2026-01-02
 */
@Data
public class LoginInfo {
    // 用户名
    private String userName;

    // 密码
    private String password;
}