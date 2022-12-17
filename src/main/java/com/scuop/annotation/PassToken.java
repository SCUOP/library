/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:23:52
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 01:24:55
 * @Description: 图书管理系统
 */
package com.scuop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}
