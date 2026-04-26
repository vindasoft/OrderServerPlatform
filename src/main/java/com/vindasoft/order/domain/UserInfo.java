/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.domain;

import lombok.Data;

/**
 * @Description: 用户信息
 * @author: jwd
 * @date: 2026-01-02
 */
@Data
public class UserInfo {
    private String userId;

    private String userName;

    private String password;

    private String status;

    private String remark;
}
