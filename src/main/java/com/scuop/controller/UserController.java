/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:38:32
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 11:53:20
 * @Description: 图书管理系统
 */
package com.scuop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scuop.annotation.UserLoginToken;
import com.scuop.domain.User;
import com.scuop.service.IUserService;
import com.scuop.utils.JWTUtil;
import com.scuop.utils.Result;

@RestController
@RequestMapping()
public class UserController {
    @Autowired
    IUserService userService;

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User userForBase = userService.getById(user.getId());
        if (userForBase == null) {
            return new Result(false, "登录失败,用户不存在");
        } else {
            if (!userForBase.getPassword().equals(user.getPassword())) {
                return new Result(false, "登录失败,密码错误");
            } else {
                String token = JWTUtil.getToken(userForBase);
                return new Result(true, token);
            }
        }
    }

    // test接口
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage() {
        return "你已通过验证";
    }
}
