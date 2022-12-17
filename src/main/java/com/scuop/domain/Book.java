/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:01:32
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 01:46:36
 * @Description: 图书管理系统
 */
package com.scuop.domain;

import lombok.Data;

@Data
public class Book {
    private Integer id;

    private String type;

    private String name;

    private String description;
}
