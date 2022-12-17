/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:20:50
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 01:41:32
 * @Description: 图书管理系统
 */
package com.scuop.utils;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.scuop.domain.User;

@Component
public class JWTUtil {
    public static String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
