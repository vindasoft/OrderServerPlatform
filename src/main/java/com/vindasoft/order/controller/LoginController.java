/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.controller;

import com.vindasoft.order.configure.TokenService;
import com.vindasoft.order.domain.LoginInfo;
import com.vindasoft.order.domain.LoginUser;
import com.vindasoft.order.domain.UserInfo;
import com.vindasoft.order.domain.response.CommonResult;
import com.vindasoft.order.service.UserService;

import com.vindasoft.order.utils.SecurityUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Description: 登录验证控制类
 * @author: jwd
 * @date: 2026-01-02
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public CommonResult login(@RequestBody LoginInfo loginInfo) {
        CommonResult result = CommonResult.success();

        // 1、校验登录接口入参参数（用户名、密码）是否为空
        if (StringUtils.isEmpty(loginInfo.getUserName()) || StringUtils.isEmpty(loginInfo.getPassword())) {
            result = CommonResult.error("登录用户名或密码为空");
            return result;
        }

        // 2、根据用户名查询用户信息: 并校验用户密码参数
        UserInfo userInfo = userService.queryUserInfoByUserName(loginInfo.getUserName());
        if (userInfo == null || !Objects.equals(userInfo.getPassword(),loginInfo.getPassword())) {
            result = CommonResult.error("用户名或密码错误");
            return result;
        }

        // 3、创建登录用户对象
        LoginUser loginUser = new LoginUser(userInfo.getUserId(), userInfo);

        // 4、生成JWT令牌
        String token = tokenService.createToken(loginUser);
        result = CommonResult.success("登录成功",token);
        return result;
    }

    /**
     * 获取当前登录用户信息
     */
    @PostMapping("/getUserInfo")
    public CommonResult getUserInfo() {
        CommonResult result = CommonResult.success();
        String userId = SecurityUtils.getUserId();
        UserInfo userInfo = userService.queryUserInfoByUserId(userId);
        result.put("data",userInfo);
        return result;
    }
}
