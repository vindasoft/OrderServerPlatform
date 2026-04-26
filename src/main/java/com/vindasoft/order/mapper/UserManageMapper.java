package com.vindasoft.order.mapper;

import com.vindasoft.order.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

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
}