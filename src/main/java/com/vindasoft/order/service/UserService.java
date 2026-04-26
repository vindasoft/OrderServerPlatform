package com.vindasoft.order.service;

import com.vindasoft.order.domain.UserInfo;

/**
 * @Description: 用户服务接口
 * @author: jwd
 */
public interface UserService {
    public UserInfo queryUserInfoByUserName(String userName);
}
