/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.service.impl;

import com.vindasoft.order.domain.UserInfo;
import com.vindasoft.order.mapper.UserManageMapper;
import com.vindasoft.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 用户服务实现类
 * @author: jwd
 * @date: 2026-01-02
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserManageMapper userManageMapper;

    @Override
    public UserInfo queryUserInfoByUserName(String userName) {
        UserInfo userInfo = userManageMapper.queryUserInfoByUserName(userName);
        return userInfo;
    }

    @Override
    public UserInfo queryUserInfoByUserId(String userId) {
        UserInfo userInfo = userManageMapper.queryUserInfoByUserId(userId);
        return userInfo;
    }

    @Override
    public List<UserInfo> queryAllUserInfos(String userName) {
        List<UserInfo> resultList = userManageMapper.queryAllUserInfos(userName);
        return resultList;
    }

    @Override
    public int addUserInfo(UserInfo userInfo) {
        return userManageMapper.addUserInfo(userInfo);
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return userManageMapper.updateUserInfo(userInfo);
    }

    @Override
    public int deleteUserInfo(String userId) {
        return userManageMapper.deleteUserInfo(userId);
    }
}
