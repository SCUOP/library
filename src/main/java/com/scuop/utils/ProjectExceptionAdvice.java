/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:01:32
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 01:11:51
 * @Description: 图书管理系统
 */
package com.scuop.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//作为springmvc的异常处理器
//@ControllerAdvice
@RestControllerAdvice
public class ProjectExceptionAdvice {
    // 拦截所有的异常信息
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        ex.printStackTrace();
        return new Result("服务器故障，请稍后再试！");
    }
}
