/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:01:32
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 01:30:58
 * @Description: 图书管理系统
 */
package com.scuop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scuop.domain.Book;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BookDao extends BaseMapper<Book> {

}
