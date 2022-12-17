/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:01:32
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 11:57:46
 * @Description: 图书管理系统
 */
package com.scuop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scuop.annotation.UserLoginToken;
import com.scuop.domain.Book;
import com.scuop.service.IBookService;
import com.scuop.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping
    @UserLoginToken
    public Result getAll() {
        return new Result(true, bookService.list());
    }

    @PostMapping
    @UserLoginToken
    public Result save(@RequestBody Book book) throws IOException {
        boolean flag = bookService.save(book);
        return new Result(flag, flag ? "添加成功" : "添加失败");
    }

    @PutMapping
    @UserLoginToken
    public Result update(@RequestBody Book book) throws IOException {
        if (book.getName().equals("123"))
            throw new IOException();
        boolean flag = bookService.modify(book);
        return new Result(flag, flag ? "修改成功" : "修改失败");
    }

    @DeleteMapping("{id}")
    @UserLoginToken
    public Result delete(@PathVariable Integer id) {
        return new Result(bookService.delete(id));
    }

    @GetMapping("{id}")
    @UserLoginToken
    public Result getById(@PathVariable Integer id) {
        return new Result(true, bookService.getById(id));
    }

    @GetMapping("{currentPage}/{pageSize}")
    @UserLoginToken
    public Result getPage(@PathVariable int currentPage, @PathVariable int pageSize, Book book) {

        IPage<Book> page = bookService.getPage(currentPage, pageSize, book);
        // 如果当前页码值大于总页码值，重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = bookService.getPage((int) page.getPages(), pageSize, book);
        }
        return new Result(true, page);
    }

}
