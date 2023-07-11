/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:20:15
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 01:34:48
 * @Description: 图书管理系统
 */
package com.scuop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    String Id;

    String username;

    String password;
}
