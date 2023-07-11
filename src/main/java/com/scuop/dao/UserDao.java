/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:30:34
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 01:30:36
 * @Description: 图书管理系统
 */
package com.scuop.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scuop.domain.User;

@Mapper
@Repository
public interface UserDao extends BaseMapper<User>{

}
