/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:31:52
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 01:40:35
 * @Description: 图书管理系统
 */
package com.scuop.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scuop.dao.UserDao;
import com.scuop.domain.User;
import com.scuop.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

}
