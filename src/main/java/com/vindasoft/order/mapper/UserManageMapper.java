package com.vindasoft.order.mapper;

import com.vindasoft.order.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: 用户管理Mapper
 * @author: jwd
 */
@Mapper
public interface UserManageMapper {
    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     * @return userInfo 用户信息
     */
    public UserInfo queryUserInfoByUserName(String userName);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return userInfo 用户信息
     */
    public UserInfo queryUserInfoByUserId(String userId);

    public List<UserInfo> queryAllUserInfos(String userName);

    /**
     * 添加用户信息
     *
     * @param userInfo 用户信息
     * @return 添加结果
     */
    public int addUserInfo(UserInfo userInfo);

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return 修改结果
     */
    public int updateUserInfo(UserInfo userInfo);

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    public int deleteUserInfo(String userId);
}