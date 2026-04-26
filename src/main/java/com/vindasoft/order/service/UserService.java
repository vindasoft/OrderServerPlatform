package com.vindasoft.order.service;

import com.vindasoft.order.domain.UserInfo;

import java.util.List;

/**
 * @Description: 用户服务接口
 * @author: jwd
 */
public interface UserService {
    public UserInfo queryUserInfoByUserName(String userName);

    public UserInfo queryUserInfoByUserId(String userId);

    public List<UserInfo> queryAllUserInfos(String userName);

    public int addUserInfo(UserInfo userInfo);

    public int updateUserInfo(UserInfo userInfo);

    public int deleteUserInfo(String userId);
}
