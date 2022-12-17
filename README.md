# **高级语言程序设计II成果报告书**


# **题目：图书管理系统** 











1. #### **项目的目的与要求**
基于SpringBoot整合spring-web，mybatis-plus，druid，JWT实现的SSM图书管理系统
1. #### **工具/准备工作**
Maven-3.8.6，Java19，Spring-Boot-2.7.6(mybatis-plus暂时未适配Spring-Boot3)，MySQL8

工作环境：vscode，MySQL Shell，windows11
1. #### **分析**
注：该项目为SSM项目，拟采用token的方式进行管理员登录的验证，以及验证后对数据库进行增删改查以实现图书管理功能。但是由于token的前端vue的拦截器目前还不会，所有只把后端的接口做了出来，测试后可以正常的生成并验证token。在实际运行做就没有将登录验证加至拦截器中而将其注释掉了。

项目采用mybatis-plus做curd的简化，在用户登录前端页面后，向用户展示数据库中的10条信息至页面，用户可以对增加图书，删除图书，修改图书信息，翻页至下页，以及通过图书的类型、书名、介绍等进行模糊查询，以便分类，这些功能都依赖于后端的Dao层的查询，后端通过处理后返回一个Result类，被自动解析为统一的Json格式供前端页面进行处理渲染，前端向后端提交数据的方式有URL提交和RequestBody提交两种方式，统一为Json格式。

项目结构分析：

library

├─ .gitignore

├─ .mvn

│    └─ wrapper

│           ├─ MavenWrapperDownloader.java

│           ├─ maven-wrapper.jar

│           └─ maven-wrapper.properties

├─ .vscode

│    └─ settings.json

├─ HELP.md

├─ file\_tree.txt

├─ library.sql

├─ mvnw

├─ mvnw.cmd

├─ pom.xml

├─ src

│    ├─ main

│    │    ├─ java

│    │    └─ resources

│    └─ test

│           └─ java


main

├─ java

│    └─ com

│           └─ scuop

│                  ├─ annotation

│                  │    ├─ PassToken.java

│                  │    └─ UserLoginToken.java

│                  ├─ config

│                  │    └─ MPConfig.java

│                  ├─ controller

│                  │    ├─ BookController.java

│                  │    └─ UserController.java

│                  ├─ dao

│                  │    ├─ BookDao.java

│                  │    └─ UserDao.java

│                  ├─ domain

│                  │    ├─ Book.java

│                  │    └─ User.java

│                  ├─ interceptors

│                  │    └─ AuthenticationInterceptor.java

│                  ├─ libraryApplication.java

│                  ├─ service

│                  │    ├─ IBookService.java

│                  │    ├─ IUserService.java

│                  │    └─ impl

│                  │           ├─ BookServiceImpl.java

│                  │           └─ UserServiceImpl.java

│                  └─ utils

│                         ├─ JWTUtil.java

│                         ├─ ProjectExceptionAdvice.java

│                         └─ Result.java

└─ resources

`       `├─ application.yml

`       `├─ static

`       `│    ├─ css

`       `│    ├─ js

`       `│    ├─ pages

`       `│    │    ├─ books.html

`       `│    │    └─ index.html

`       `│    └─ plugins                

`       `└─ templates

IOC容器中管理的所有bean：

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.001.png)

核心类：

核心实体类（domain）：

Book类：

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:46:36

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.domain;

import lombok.*Data*;

@*Data*

public class Book {

private Integer id;

`    `private String type;

private String name;

`    `private String description;

}
#### 前后端交互Result类（Json传输）：
/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:09:27

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.utils;

import lombok.*Data*;

@*Data*

public class Result {

`    `private Boolean flag;

`    `private Object data;

`    `private String msg;

`    `public Result() {

`    `}

`    `public Result(Boolean *flag*) {

`        `this.flag = *flag*;

`    `}

`    `public Result(Boolean *flag*, Object *data*) {

`        `this.flag = *flag*;

`        `this.data = *data*;

`    `}

`    `public Result(Boolean *flag*, String *msg*) {

`        `this.flag = *flag*;

`        `this.msg = *msg*;

`    `}

`    `public Result(String *msg*) {

`        `this.flag = false;

`        `this.msg = *msg*;

`    `}

}

#### 数据库sql文件（library.sql）:
CREATE DATABASE  IF NOT EXISTS `library` /\*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4\_0900\_ai\_ci \*/ /\*!80016 DEFAULT ENCRYPTION='N' \*/;

USE `library`;

-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86\_64)

\--

-- Host: localhost    Database: library

\-- ------------------------------------------------------

-- Server version 8.0.31

/\*!40101 SET @OLD\_CHARACTER\_SET\_CLIENT=@@CHARACTER\_SET\_CLIENT \*/;

/\*!40101 SET @OLD\_CHARACTER\_SET\_RESULTS=@@CHARACTER\_SET\_RESULTS \*/;

/\*!40101 SET @OLD\_COLLATION\_CONNECTION=@@COLLATION\_CONNECTION \*/;

/\*!50503 SET NAMES utf8 \*/;

/\*!40103 SET @OLD\_TIME\_ZONE=@@TIME\_ZONE \*/;

/\*!40103 SET TIME\_ZONE='+00:00' \*/;

/\*!40014 SET @OLD\_UNIQUE\_CHECKS=@@UNIQUE\_CHECKS, UNIQUE\_CHECKS=0 \*/;

/\*!40014 SET @OLD\_FOREIGN\_KEY\_CHECKS=@@FOREIGN\_KEY\_CHECKS, FOREIGN\_KEY\_CHECKS=0 \*/;

/\*!40101 SET @OLD\_SQL\_MODE=@@SQL\_MODE, SQL\_MODE='NO\_AUTO\_VALUE\_ON\_ZERO' \*/;

/\*!40111 SET @OLD\_SQL\_NOTES=@@SQL\_NOTES, SQL\_NOTES=0 \*/;

\--

-- Table structure for table `book`

\--

DROP TABLE IF EXISTS `book`;

/\*!40101 SET @saved\_cs\_client     = @@character\_set\_client \*/;

/\*!50503 SET character\_set\_client = utf8mb4 \*/;

CREATE TABLE `book` (

`  ``id` *int* NOT NULL AUTO\_INCREMENT,

`  ``type` *varchar*(64) NOT NULL,

`  ``name` *varchar*(64) NOT NULL,

`  ``description` *varchar*(1024) DEFAULT NULL,

`  `PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO\_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4\_0900\_ai\_ci;

/\*!40101 SET character\_set\_client = @saved\_cs\_client \*/;

\--

-- Dumping data for table `book`

\--

LOCK TABLES `book` WRITE;

/\*!40000 ALTER TABLE `book` DISABLE KEYS \*/;

INSERT INTO `book` VALUES (1,'计算机理论','高级语言程序设计','C++程序设计教材'),(2,'数学','概率论','内容全面，例题和习题丰富，结构层次性强，浅显易懂，能够满足不同读者的需求'),(3,'英语','大学英语-1','大一上英语教材'),(4,'文学','悲惨世界','法国作家维克多·雨果于1862年所发表的一部长篇小说，是19世纪最著名的小说之一。小说描绘19世纪初20年间几个法国人物的生活背景，涵盖拿破仑战争和1832年巴黎共和党人起义等政治现象叙述。'),(5,'文学','叔本华思想随笔','叔本华是德国著名哲学家，唯意志主义和现代悲观主义创始人。 《叔本华思想随笔》选自叔本华的后期著作《附录和补遗》与《作为意欲和表象的世界》第二卷，《叔本华思想随笔》虽然讨论的话题众多，但里面贯穿着的基本思想主线清晰可辨。叔本华的过人之处就在于把真理裹以最朴素的语言外衣。'),(6,'文学','悉达多','《悉达多》是黑塞的第九部作品，1922年在德国出版，通过对主人公悉达多身上的两个“自我”——理性的无限的“自我”和感性的有限的“自我”——的描写，黑塞探讨了个人如何在有限的生命中追求无限的、永恒的人生境界的问题。读者从中既可以洞察作家对人性的热爱与敬畏，对人生和宇宙的充满睿智的理解，又能够感受到他对传统的人道主义理想的呼唤和向往，同时，还可以领略到作为西方人的作者对东方尤其是中国思想智慧的接受与借鉴。'),(7,'文学','肖申克的救赎','《肖申克的救赎》是美国作家斯蒂芬·埃德温·金的中篇小说，也是其代表作。收录于小说合集《四季奇谭》中，副标题为“春天的希望”。'),(8,'文学','白夜行','《白夜行》是日本作家东野圭吾创作的长篇小说，也是其代表作。该小说于1997年1月至1999年1月间连载于期刊，单行本1999年8月在日本发行。故事围绕着一对有着不同寻常情愫的小学生展开。1973年，大阪的一栋废弃建筑内发现了一具男尸，此后19年，嫌疑人之女雪穗与被害者之子桐原亮司走上截然不同的人生道路，一个跻身上流社会，一个却在底层游走，而他们身边的人，却接二连三地离奇死去，警察经过19年的艰苦追踪，终于使真相大白。'),(9,'文学','且听风吟','《且听风吟》 是日本作家村上春树创作的一部中篇小说，发表于1979年。'),(10,'文学','一九八四','《一九八四》（Nineteen Eighty-Four）是英国左翼作家乔治·奥威尔于1949年出版的长篇政治小说。'),(11,'文学','刺杀骑士团长','《刺杀骑士团长》是村上春树撰写的超现实主义小说。该书中文译本于2018年由上海译文出版社出版发行，译者是林少华。');

/\*!40000 ALTER TABLE `book` ENABLE KEYS \*/;

UNLOCK TABLES;

\--

-- Table structure for table `user`

\--

DROP TABLE IF EXISTS `user`;

/\*!40101 SET @saved\_cs\_client     = @@character\_set\_client \*/;

/\*!50503 SET character\_set\_client = utf8mb4 \*/;

CREATE TABLE `user` (

`  ``id` *int* NOT NULL AUTO\_INCREMENT,

`  ``username` *varchar*(45) NOT NULL,

`  ``password` *varchar*(45) NOT NULL,

`  `PRIMARY KEY (`id`,`password`,`username`)

) ENGINE=InnoDB AUTO\_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4\_0900\_ai\_ci;

/\*!40101 SET character\_set\_client = @saved\_cs\_client \*/;

\--

-- Dumping data for table `user`

\--

LOCK TABLES `user` WRITE;

/\*!40000 ALTER TABLE `user` DISABLE KEYS \*/;

INSERT INTO `user` VALUES (1,'root','123');

/\*!40000 ALTER TABLE `user` ENABLE KEYS \*/;

UNLOCK TABLES;

/\*!40103 SET TIME\_ZONE=@OLD\_TIME\_ZONE \*/;

/\*!40101 SET SQL\_MODE=@OLD\_SQL\_MODE \*/;

/\*!40014 SET FOREIGN\_KEY\_CHECKS=@OLD\_FOREIGN\_KEY\_CHECKS \*/;

/\*!40014 SET UNIQUE\_CHECKS=@OLD\_UNIQUE\_CHECKS \*/;

/\*!40101 SET CHARACTER\_SET\_CLIENT=@OLD\_CHARACTER\_SET\_CLIENT \*/;

/\*!40101 SET CHARACTER\_SET\_RESULTS=@OLD\_CHARACTER\_SET\_RESULTS \*/;

/\*!40101 SET COLLATION\_CONNECTION=@OLD\_COLLATION\_CONNECTION \*/;

/\*!40111 SET SQL\_NOTES=@OLD\_SQL\_NOTES \*/;

-- Dump completed on 2022-12-17  1:51:31

1. #### **完整程序代码**
仅列出后端springboot代码：

annotation--注解 config--配置 controller--控制器 dao--数据映射层 domain--实体类 interceptors--拦截器 service--服务 utils--工具

application.yaml

server:

`  `port: 80

spring:

`  `datasource:

`    `druid:

`      `driver-class-name: com.mysql.cj.jdbc.Driver

`      `url: jdbc:mysql://localhost:3306/library?serverTimezone=UTC

`      `username: root

`      `password: password

mybatis-plus:

`  `global-config:

`    `db-config:

`      `id-type: auto

`  `configuration:

`    `log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

libraryApplication.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:42:25

` `\* @Description: 图书管理系统

` `\*/

package com.scuop;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.*SpringBootApplication*;

@*SpringBootApplication*

public class libraryApplication {

`    `public static *void* main(String[] *args*) {

`        `SpringApplication.run(libraryApplication.class, *args*);

`    `}

}

utils.JWTUtil,java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:20:50

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:41:32

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.utils;

import org.springframework.stereotype.*Component*;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;

import com.scuop.domain.User;

@*Component*

public class JWTUtil {

`    `public static String getToken(User *user*) {

`        `String token = "";

`        `token = JWT.create().withAudience(*user*.getId())

`                `.sign(Algorithm.HMAC256(*user*.getPassword()));

`        `return token;

`    `}

}

utils.ProjectExceptionAdvice.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:11:51

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.utils;

import org.springframework.web.bind.annotation.*ExceptionHandler*;

import org.springframework.web.bind.annotation.*RestControllerAdvice*;

//作为springmvc的异常处理器

//@ControllerAdvice

@*RestControllerAdvice*

public class ProjectExceptionAdvice {

`    `// 拦截所有的异常信息

`    `@*ExceptionHandler*(Exception.class)

`    `public Result doException(Exception *ex*) {

`        `*ex*.printStackTrace();

`        `return new Result("服务器故障，请稍后再试！");

`    `}

}

utils.Result.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:09:27

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.utils;

import lombok.*Data*;

@*Data*

public class Result {

`    `private Boolean flag;

`    `private Object data;

`    `private String msg;

`    `public Result() {

`    `}

`    `public Result(Boolean *flag*) {

`        `this.flag = *flag*;

`    `}

`    `public Result(Boolean *flag*, Object *data*) {

`        `this.flag = *flag*;

`        `this.data = *data*;

`    `}

`    `public Result(Boolean *flag*, String *msg*) {

`        `this.flag = *flag*;

`        `this.msg = *msg*;

`    `}

`    `public Result(String *msg*) {

`        `this.flag = false;

`        `this.msg = *msg*;

`    `}

}

service.IBookService.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:09:38

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.service.IService;

import com.scuop.domain.Book;

public interface IBookService extends IService<Book> {

`    `*boolean* saveBook(Book *book*);

`    `*boolean* modify(Book *book*);

`    `*boolean* delete(Integer *id*);

`    `IPage<Book> getPage(*int* *currentPage*, *int* *pageSize*);

`    `IPage<Book> getPage(*int* *currentPage*, *int* *pageSize*, Book *book*);

}

service.IUserService.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:29:48

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:29:50

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.scuop.domain.User;

public interface IUserService extends IService<User> {

}

service.impl.BookServiceImpl.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:31:52

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:40:35

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.service.impl;

import org.springframework.stereotype.*Service*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.scuop.dao.UserDao;

import com.scuop.domain.User;

import com.scuop.service.IUserService;

@*Service*

public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

}

service.impl.UserServiceImpl.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:31:52

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:40:35

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.service.impl;

import org.springframework.stereotype.*Service*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.scuop.dao.UserDao;

import com.scuop.domain.User;

import com.scuop.service.IUserService;

@*Service*

public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

}

interceptors.AuthenticationInterceptor.java(登录验证拦截器)

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:21:23

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 12:04:53

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.interceptors;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.*Autowired*;

import org.springframework.web.method.HandlerMethod;

import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;

import com.auth0.jwt.JWTVerifier;

import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.JWTDecodeException;

import com.auth0.jwt.exceptions.JWTVerificationException;

import com.scuop.annotation.*PassToken*;

import com.scuop.annotation.*UserLoginToken*;

import com.scuop.domain.User;

import com.scuop.service.IUserService;

// 认证拦截器

public class AuthenticationInterceptor implements HandlerInterceptor {

`    `@*Autowired*

`    `IUserService userService;

`    `@*Override*

`    `public *boolean* preHandle(HttpServletRequest *httpServletRequest*, HttpServletResponse *httpServletResponse*,

`            `Object *object*) throws Exception {

`        `String token = *httpServletRequest*.getHeader("token");// 从 http 请求头中取出 token

`        `// 如果不是映射到方法直接通过

`        `if (!(*object* instanceof HandlerMethod)) {

`            `return true;

`        `}

`        `HandlerMethod handlerMethod = (HandlerMethod) *object*;

`        `Method method = handlerMethod.getMethod();

`        `// 检查是否有passtoken注释，有则跳过认证

`        `if (method.isAnnotationPresent(*PassToken*.class)) {

`            `*PassToken* passToken = method.getAnnotation(*PassToken*.class);

`            `if (passToken.required()) {

`                `return true;

`            `}

`        `}

`        `// 检查有没有需要用户权限的注解

`        `if (method.isAnnotationPresent(*UserLoginToken*.class)) {

`            `*UserLoginToken* userLoginToken = method.getAnnotation(*UserLoginToken*.class);

`            `if (userLoginToken.required()) {

`                `// 执行认证

`                `if (token == null) {

`                    `throw new RuntimeException("无token，请重新登录");

`                `}

`                `// 获取 token 中的 user id

`                `String userId;

`                `try {

`                    `userId = JWT.decode(token).getAudience().get(0);

`                `} catch (JWTDecodeException j) {

`                    `throw new RuntimeException("401");

`                `}

`                `User user = userService.getById(userId);

`                `if (user == null) {

`                    `throw new RuntimeException("用户不存在，请重新登录");

`                `}

`                `// 验证 token

`                `JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();

`                `try {

`                    `jwtVerifier.verify(token);

`                `} catch (JWTVerificationException e) {

`                    `throw new RuntimeException("401");

`                `}

`                `return true;

`            `}

`        `}

`        `return true;

`    `}

}

domain.Book.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:46:36

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.domain;

import lombok.*Data*;

@*Data*

public class Book {

`    `private Integer id;

`    `private String type;

`    `private String name;

`    `private String description;

}

domain.User.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:20:15

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:34:48

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.domain;

import lombok.*AllArgsConstructor*;

import lombok.*Data*;

import lombok.*NoArgsConstructor*;

@*Data*

@*NoArgsConstructor*

@*AllArgsConstructor*

public class User {

`    `String Id;

`    `String username;

`    `String password;

}

dao.BookDao.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:30:58

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.scuop.domain.Book;

import org.apache.ibatis.annotations.*Mapper*;

import org.springframework.stereotype.*Repository*;

@*Mapper*

@*Repository*

public interface BookDao extends BaseMapper<Book> {

}

dao.UserDao.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:30:34

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:30:36

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.dao;

import org.apache.ibatis.annotations.*Mapper*;

import org.springframework.stereotype.*Repository*;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.scuop.domain.User;

@*Mapper*

@*Repository*

public interface UserDao extends BaseMapper<User>{

}

controller.BookController.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 11:57:46

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.scuop.annotation.*UserLoginToken*;

import com.scuop.domain.Book;

import com.scuop.service.IBookService;

import com.scuop.utils.Result;

import org.springframework.beans.factory.annotation.*Autowired*;

import org.springframework.web.bind.annotation.\*;

import java.io.IOException;

@*RestController*

@*RequestMapping*("/books")

public class BookController {

`    `@*Autowired*

`    `private IBookService bookService;

`    `@*GetMapping*

`    `@*UserLoginToken*

`    `public Result getAll() {

`        `return new Result(true, bookService.list());

`    `}

`    `@*PostMapping*

`    `@*UserLoginToken*

`    `public Result save(@*RequestBody* Book *book*) throws IOException {

`        `*boolean* flag = bookService.save(*book*);

`        `return new Result(flag, flag ? "添加成功" : "添加失败");

`    `}

`    `@*PutMapping*

`    `@*UserLoginToken*

`    `public Result update(@*RequestBody* Book *book*) throws IOException {

`        `if (*book*.getName().equals("123"))

`            `throw new IOException();

`        `*boolean* flag = bookService.modify(*book*);

`        `return new Result(flag, flag ? "修改成功" : "修改失败");

`    `}

`    `@*DeleteMapping*("{id}")

`    `@*UserLoginToken*

`    `public Result delete(@*PathVariable* Integer *id*) {

`        `return new Result(bookService.delete(*id*));

`    `}

`    `@*GetMapping*("{id}")

`    `@*UserLoginToken*

`    `public Result getById(@*PathVariable* Integer *id*) {

`        `return new Result(true, bookService.getById(*id*));

`    `}

`    `@*GetMapping*("{currentPage}/{pageSize}")

`    `@*UserLoginToken*

`    `public Result getPage(@*PathVariable* *int* *currentPage*, @*PathVariable* *int* *pageSize*, Book *book*) {

`        `IPage<Book> page = bookService.getPage(*currentPage*, *pageSize*, *book*);

`        `// 如果当前页码值大于总页码值，重新执行查询操作，使用最大页码值作为当前页码值

`        `if (*currentPage* > page.getPages()) {

`            `page = bookService.getPage((*int*) page.getPages(), *pageSize*, *book*);

`        `}

`        `return new Result(true, page);

`    `}

}

controller.UserController.java

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:38:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 11:53:20

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.controller;

import org.springframework.beans.factory.annotation.*Autowired*;

import org.springframework.web.bind.annotation.*GetMapping*;

import org.springframework.web.bind.annotation.*PostMapping*;

import org.springframework.web.bind.annotation.*RequestBody*;

import org.springframework.web.bind.annotation.*RequestMapping*;

import org.springframework.web.bind.annotation.*RestController*;

import com.scuop.annotation.*UserLoginToken*;

import com.scuop.domain.User;

import com.scuop.service.IUserService;

import com.scuop.utils.JWTUtil;

import com.scuop.utils.Result;

@*RestController*

@*RequestMapping*()

public class UserController {

`    `@*Autowired*

`    `IUserService userService;

`    `// 登录

`    `@*PostMapping*("/login")

`    `public Result login(@*RequestBody* User *user*) {

`        `User userForBase = userService.getById(*user*.getId());

`        `if (userForBase == null) {

`            `return new Result(false, "登录失败,用户不存在");

`        `} else {

`            `if (!userForBase.getPassword().equals(*user*.getPassword())) {

`                `return new Result(false, "登录失败,密码错误");

`            `} else {

`                `String token = JWTUtil.getToken(userForBase);

`                `return new Result(true, token);

`            `}

`        `}

`    `}

`    `// test接口

`    `@*UserLoginToken*

`    `@*GetMapping*("/getMessage")

`    `public String getMessage() {

`        `return "你已通过验证";

`    `}

}

config.MPConfig

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:01:32

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 13:21:55

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

// import com.scuop.interceptors.AuthenticationInterceptor;

import org.springframework.context.annotation.*Bean*;

import org.springframework.context.annotation.*Configuration*;

// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@*Configuration*

public class MPConfig implements WebMvcConfigurer {

`    `@*Bean*

`    `public MybatisPlusInterceptor mybatisPlusInterceptor() {

`        `MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

`        `interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

`        `return interceptor;

`    `}

`    `// 登录验证拦截器

`    `// @Override

`    `// public void addInterceptors(InterceptorRegistry registry) {

`    `// registry.addInterceptor(authenticationInterceptor())

`    `// .addPathPatterns("/\*\*");

`    `// }

`    `// @Bean

`    `// public AuthenticationInterceptor authenticationInterceptor() {

`    `// return new AuthenticationInterceptor();

`    `// }

}

annotation.UserLoginToken.java (验证登录注解)

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:24:23

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:24:25

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.annotation;

import java.lang.annotation.ElementType;

import java.lang.annotation.*Retention*;

import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.*Target*;

@*Target*({ElementType.METHOD, ElementType.TYPE})

@*Retention*(RetentionPolicy.RUNTIME)

public @interface *UserLoginToken* {

`    `*boolean* required() default true;

}

annotation.PassToken.java 

/\*

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 01:23:52

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 01:24:55

` `\* @Description: 图书管理系统

` `\*/

package com.scuop.annotation;

import java.lang.annotation.ElementType;

import java.lang.annotation.*Retention*;

import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.*Target*;

@*Target*({ ElementType.METHOD, ElementType.TYPE })

@*Retention*(RetentionPolicy.RUNTIME)

public @interface *PassToken* {

`    `*boolean* required() default true;

}

` `前端代码：

books.html

<!DOCTYPE html>

<html>

<head>

`    `<!-- 页面meta -->

`    `<meta charset="utf-8">

`    `<meta http-equiv="X-UA-Compatible" content="IE=edge">

`    `<title>library</title>

`    `<meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">

`    `<!-- 引入样式 -->

`    `<link rel="stylesheet" href="../plugins/elementui/index.css">

`    `<link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">

`    `<link rel="stylesheet" href="../css/style.css">

</head>

<body class="hold-transition">

`    `<div id="app">

`        `<div class="content-header">

`            `<h1>图书管理</h1>

`        `</div>

`        `<div class="app-container">

`            `<div class="box">

`                `<div class="filter-container">

`                    `<el-input placeholder="图书类别" v-model="pagination.type" style="width: 200px;"

`                        `class="filter-item"></el-input>

`                    `<el-input placeholder="图书名称" v-model="pagination.name" style="width: 200px;"

`                        `class="filter-item"></el-input>

`                    `<el-input placeholder="图书描述" v-model="pagination.description" style="width: 200px;"

`                        `class="filter-item"></el-input>

`                    `<el-button @click="getAll()" class="dalfBut">查询</el-button>

`                    `<el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>

`                `</div>

`                `<el-table size="small" current-row-key="id" :data="dataList" stripe highlight-current-row>

`                    `<el-table-column type="index" align="center" label="序号"></el-table-column>

`                    `<el-table-column prop="type" label="图书类别" align="center"></el-table-column>

`                    `<el-table-column prop="name" label="图书名称" align="center"></el-table-column>

`                    `<el-table-column prop="description" label="描述" align="center"></el-table-column>

`                    `<el-table-column label="操作" align="center">

`                        `<template slot-scope="scope">

`                            `<el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>

`                            `<el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>

`                        `</template>

`                    `</el-table-column>

`                `</el-table>

`                `<!--分页组件-->

`                `<div class="pagination-container">

`                    `<el-pagination class="pagiantion" @current-change="handleCurrentChange"

`                        `:current-page="pagination.currentPage" :page-size="pagination.pageSize"

`                        `layout="total, prev, pager, next, jumper" :total="pagination.total">

`                    `</el-pagination>

`                `</div>

`                `<!-- 新增标签弹层 -->

`                `<div class="add-form">

`                    `<el-dialog title="新增图书" :visible.sync="dialogFormVisible">

`                        `<el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right"

`                            `label-width="100px">

`                            `<el-row>

`                                `<el-col :span="12">

`                                    `<el-form-item label="图书类别" prop="type">

`                                        `<el-input v-model="formData.type" />

`                                    `</el-form-item>

`                                `</el-col>

`                                `<el-col :span="12">

`                                    `<el-form-item label="图书名称" prop="name">

`                                        `<el-input v-model="formData.name" />

`                                    `</el-form-item>

`                                `</el-col>

`                            `</el-row>

`                            `<el-row>

`                                `<el-col :span="24">

`                                    `<el-form-item label="描述">

`                                        `<el-input v-model="formData.description" type="textarea"></el-input>

`                                    `</el-form-item>

`                                `</el-col>

`                            `</el-row>

`                        `</el-form>

`                        `<div slot="footer" class="dialog-footer">

`                            `<el-button @click="cancel()">取消</el-button>

`                            `<el-button type="primary" @click="handleAdd()">确定</el-button>

`                        `</div>

`                    `</el-dialog>

`                `</div>

`                `<!-- 编辑标签弹层 -->

`                `<div class="add-form">

`                    `<el-dialog title="编辑检查项" :visible.sync="dialogFormVisible4Edit">

`                        `<el-form ref="dataEditForm" :model="formData" :rules="rules" label-position="right"

`                            `label-width="100px">

`                            `<el-row>

`                                `<el-col :span="12">

`                                    `<el-form-item label="图书类别" prop="type">

`                                        `<el-input v-model="formData.type" />

`                                    `</el-form-item>

`                                `</el-col>

`                                `<el-col :span="12">

`                                    `<el-form-item label="图书名称" prop="name"

`                                        `<el-input v-model="formData.name" />

`                                    `</el-form-item>

`                                `</el-col>

`                            `</el-row>

`                            `<el-row>

`                                `<el-col :span="24">

`                                    `<el-form-item label="描述">

`                                        `<el-input v-model="formData.description" type="textarea"></el-input>

`                                    `</el-form-item>

`                                `</el-col>

`                            `</el-row>

`                        `</el-form>

`                        `<div slot="footer" class="dialog-footer">

`                            `<el-button @click="cancel()">取消</el-button>

`                            `<el-button type="primary" @click="handleEdit()">确定</el-button>

`                        `</div>

`                    `</el-dialog>

`                `</div>

`            `</div>

`        `</div>

`    `</div>

</body>

<!-- 引入组件库 -->

<script src="../js/vue.js"></script>

<script src="../plugins/elementui/index.js"></script>

<script type="text/javascript" src="../js/jquery.min.js"></script>

<script src="../js/axios-0.18.0.js"></script>

<script>

`    `*var* vue = new Vue({

`        `el: '#app',

`        `data: {

`            `dataList: [],//当前页要展示的列表数据

`            `dialogFormVisible: false,//添加表单是否可见

`            `dialogFormVisible4Edit: false,//编辑表单是否可见

`            `formData: {},//表单数据

`            `rules: {//校验规则

`                `type: [{ required: true, message: '图书类别为必填项', trigger: 'blur' }],

`                `name: [{ required: true, message: '图书名称为必填项', trigger: 'blur' }]

`            `},

`            `pagination: {//分页相关模型数据

`                `currentPage: 1,//当前页码

`                `pageSize: 10,//每页显示的记录数

`                `total: 0,//总记录数

`                `type: "",

`                `name: "",

`                `description: ""

`            `}

`        `},

`        `//钩子函数，VUE对象初始化完成后自动执行

`        `created() {

`            `//调用查询全部数据的操作

`            `this.getAll();

`        `},

`        `methods: {

`            `//列表

`            `// getAll() {

`            `//     //发送异步请求

`            `//     axios.get("/books").then((res)=>{

`            `//         // console.log(res.data);

`            `//         this.dataList = res.data.data;

`            `//     });

`            `// },

`            `//分页查询

`            `getAll() {

`                `//组织参数，拼接url请求地址

`                `// console.log(this.pagination.type);

`                `param = "?type=" + this.pagination.type;

`                `param += "&name=" + this.pagination.name;

`                `param += "&description=" + this.pagination.description;

`                `// console.log(param);

`                `//发送异步请求

`                `axios.get("/books/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then((*res*) *=>* {

`                    `this.pagination.pageSize = *res*.data.data.size;

`                    `this.pagination.currentPage = *res*.data.data.current;

`                    `this.pagination.total = *res*.data.data.total;

`                    `this.dataList = *res*.data.data.records;

`                `});

`            `},

`            `//切换页码

`            `handleCurrentChange(*currentPage*) {

`                `//修改页码值为当前选中的页码值

`                `this.pagination.currentPage = *currentPage*;

`                `//执行查询

`                `this.getAll();

`            `},

`            `//弹出添加窗口

`            `handleCreate() {

`                `this.dialogFormVisible = true;

`                `this.resetForm();

`            `},

`            `//重置表单

`            `resetForm() {

`                `this.formData = {};

`            `},

`            `//添加

`            `handleAdd() {

`                `axios.post("/books", this.formData).then((*res*) *=>* {

`                    `//判断当前操作是否成功

`                    `if (*res*.data.flag) {

`                        `//1.关闭弹层

`                        `this.dialogFormVisible = false;

`                        `this.$message.success(*res*.data.msg);

`                    `} else {

`                        `this.$message.error(*res*.data.msg);

`                    `}

`                `}).finally(() *=>* {

`                    `//2.重新加载数据

`                    `this.getAll();

`                `});

`            `},

`            `//取消

`            `cancel() {

`                `this.dialogFormVisible = false;

`                `this.dialogFormVisible4Edit = false;

`                `this.$message.info("当前操作取消");

`            `},

`            `// 删除

`            `handleDelete(*row*) {

`                `// console.log(row);

`                `this.$confirm("此操作永久删除当前信息，是否继续？", "提示", { type: "info" }).then(() *=>* {

`                    `axios.delete("/books/" + *row*.id).then((*res*) *=>* {

`                        `if (*res*.data.flag) {

`                            `this.$message.success("删除成功");

`                        `} else {

`                            `this.$message.error("数据同步失败，自动刷新");

`                        `}

`                    `}).finally(() *=>* {

`                        `//2.重新加载数据

`                        `this.getAll();

`                    `});

`                `}).catch(() *=>* {

`                    `this.$message.info("取消操作");

`                `});

`            `},

`            `//弹出编辑窗口

`            `handleUpdate(*row*) {

`                `axios.get("/books/" + *row*.id).then((*res*) *=>* {

`                    `if (*res*.data.flag && *res*.data.data != null) {

`                        `this.dialogFormVisible4Edit = true;

`                        `this.formData = *res*.data.data;

`                    `} else {

`                        `this.$message.error("数据同步失败，自动刷新");

`                    `}

`                `}).finally(() *=>* {

`                    `//2.重新加载数据

`                    `this.getAll();

`                `});

`            `},

`            `//修改

`            `handleEdit() {

`                `axios.put("/books", this.formData).then((*res*) *=>* {

`                    `//判断当前操作是否成功

`                    `if (*res*.data.flag) {

`                        `//1.关闭弹层

`                        `this.dialogFormVisible4Edit = false;

`                        `this.$message.success("修改成功");

`                    `} else {

`                        `this.$message.error("修改失败");

`                    `}

`                `}).finally(() *=>* {

`                    `//2.重新加载数据

`                    `this.getAll();

`                `});

`            `},

`            `//条件查询

`        `}

`    `})

</script>

</html>

index.html

<!--

` `\* @Author: SCUOP

` `\* @Date: 2022-12-17 12:12:35

` `\* @LastEditors: SCUOP

` `\* @LastEditTime: 2022-12-17 14:43:41

` `\* @Description: 图书管理系统

-->

<!DOCTYPE html>

<html>

<head>

`    `<!-- 页面meta -->

`    `<meta charset="utf-8">

`    `<meta http-equiv="X-UA-Compatible" content="IE=edge">

`    `<title>library</title>

`    `<meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">

`   `<!-- 引入样式 -->

`    `<link rel="stylesheet" href="../plugins/elementui/index.css">

`    `<link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">

`    `<link rel="stylesheet" href="../css/style.css">

</head>

<body class="hold-transition">

`    `<div id="app">

`        `<div class="content-header">

`            `<h1>图书管理</h1>

`        `</div>

`        `<div class="app-container">

`            `<div class="box">

`                `<div class="filter-container">

`                    `<el-input placeholder="ID" v-model="user.id" style="width: 200px;" class="filter-item"

`                        `type="number"></el-input>

`                    `<el-input placeholder="账号" v-model="user.username" style="width: 200px;" class="filter-item"

`                        `clearable></el-input>

`                    `<el-input placeholder="密码" v-model="user.password" style="width: 200px;" class="filter-item"

`                        `clearable show-password></el-input>

`                    `<el-button @click="login()" class="dalfBut">登录</el-button>

`                `</div>

`            `</div>

`        `</div>

`    `</div>

</body>

<!-- 引入组件库 -->

<script src="../js/vue.js"></script>

<script src="../plugins/elementui/index.js"></script>

<script type="text/javascript" src="../js/jquery.min.js"></script>

<script src="../js/axios-0.18.0.js"></script>

<script>

`    `*var* vue = new Vue({

`        `el: '#app',

`        `data: {

`            `user: {

`                `id: 1,

`                `username: "",

`                `password: "",

`            `}

`        `},

`        `methods: {

`            `login() {

`                `axios.post("/index", this.user).then((*res*) *=>* {

`                    `//判断当前操作是否成功

`                    `if (*res*.data.flag) {

`                        `localStorage.setItem("token", *res*.data.msg);

`                    `} else {

`                        `this.$message.error(*res*.data.msg);

`                    `}

`                `})

`            `},

`        `}

`    `})

</script>

</html>

列出所编写的全部程序代码。如果由几个文件组成，请分别列出每个文件的程序代码。

**温馨小帖士**：程序代码中应有适当的**注释**，**注释**也是**评分**的组成部分之一。
1. #### **测试与结论**
由于前端没有搞定token获取后的保存（vue的拦截器），因此登录不演示。

登录页面：

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.002.png)

演示：

1. 打开页面http://localhost/pages/books.html

此时前端向后台发送GET请求http://localhost/books/1/10表示获取第一页的10条信息

后端日志

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.003.png)

前端显示：

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.004.png)

分页功能：

点击下一页或者输入2跳转至下一页：

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.005.png)

后台日志：

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.006.png)

CURD：

增：

点击新建 弹出新建弹层

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.007.png)

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.008.png)

其中类型和名称为必填项 点击确定，显示添加成功并刷新页面发现最后一条数据添加成功

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.009.png)

点击取消 会出现当前操作取消的提示

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.010.png)

删：

点击删除，弹出提示，是否进行删除，点击取消，出现当前操作取消的提示，点击确定，显示删除成功，并刷新页面。

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.011.png)

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.012.png)

有可能会出现两个用户同时使用，这是删除就会失败，弹出 数据同步失败，自动刷新 错误

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.013.png)

删除的日志：

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.014.png)

改：

对某条数据点击修改按钮，出现修改弹层

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.015.png)

点击确定，显示修改成功，取消显示取消当前操作。

后台日志：

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.016.png)

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.017.png)

查：

通过图书类别 图书名称 图书描述进行模糊查找，原理是LIKE的SQL语句。

输入查找内容 点击查找

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.018.png)

后台日志

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.019.png)

![](Aspose.Words.08f62c17-7bf6-43e4-a1d6-b5950f17a860.020.png)

最后对maven进行clean package操作，生成target文件library-0.0.1-SNAPSHOT.jar，生成微服务运行。
#### **六、项目总结**
1、列出项目具体实现的**功能**。

\1. 分页页面，呈现给用户便于操作交互。

\2. 对library数据库的增删改查。

\3. 实现了对大量图书进行管理的目的。

\4. 对spring-web，mybatis-plus，druid，JWT进行了完整正确的整合。

2、列出项目中你认为 “**具有创新性**”或“**具有拓展性**”的**功能实现**或**实现思想**。

`  `1. 使用lomlok，极大的简化了bean和java实体类的书写。

`  `2. 使用mybatis-plus而非mybatis，简化数据映射层的书写以及对数据库进行CRUD操作的简化。

`  `3. 使用springboot内置的tomcat服务器进行图形化界面的渲染，采用vue和element-ui进行前端的书写。

`  `4. 采用JWT进行安全认证

3、如果项目的功能有不足之处，请加以列出。

`  `1. 由于无法保存从后端的localhost/login获取的token，没有实现前端的登录验证。

`  `2. Result的设计不够完美，没有接受http-code的变量。

`  `3. vue的拦截器没有实现。

**温馨小帖士**：请认真填写上面的“1”和“2”这两部分，这两部分写得越丰富越好，这两部分是**评分**的**重要依据**。
