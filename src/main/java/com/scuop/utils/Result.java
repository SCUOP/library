/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:01:32
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 01:09:27
 * @Description: 图书管理系统
 */
package com.scuop.utils;

import lombok.Data;

@Data
public class Result {
    private Boolean flag;
    private Object data;
    private String msg;

    public Result() {
    }

    public Result(Boolean flag) {
        this.flag = flag;
    }

    public Result(Boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public Result(Boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public Result(String msg) {
        this.flag = false;
        this.msg = msg;
    }
}
