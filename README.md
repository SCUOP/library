
# 高级语言程序设计II成果报告书

# 题目：图书管理系统

#### 项目的目的与要求

基于SpringBoot整合spring\-web，mybatis\-plus，druid，JWT实现的SSM图书管理系统

#### 工具/准备工作

Maven\-3\.8\.6，Java19，Spring\-Boot\-2\.7\.6\(mybatis\-plus暂时未适配Spring\-Boot3\)，MySQL8

工作环境：vscode，MySQL Shell，windows11

本地运行路径: http://localhost:8080/pages/books.html

#### 分析

注：该项目为SSM项目，拟采用token的方式进行管理员登录的验证，以及验证后对数据库进行增删改查以实现图书管理功能。但是由于token的前端vue的拦截器目前还不会，所有只把后端的接口做了出来，测试后可以正常的生成并验证token。在实际运行做就没有将登录验证加至拦截器中而将其注释掉了。

项目采用mybatis\-plus做curd的简化，在用户登录前端页面后，向用户展示数据库中的10条信息至页面，用户可以对增加图书，删除图书，修改图书信息，翻页至下页，以及通过图书的类型、书名、介绍等进行模糊查询，以便分类，这些功能都依赖于后端的Dao层的查询，后端通过处理后返回一个Result类，被自动解析为统一的Json格式供前端页面进行处理渲染，前端向后端提交数据的方式有URL提交和RequestBody提交两种方式，统一为Json格式。

六、项目总结

1、列出项目具体实现的功能。

1\. 分页页面，呈现给用户便于操作交互。

2\. 对library数据库的增删改查。

3\. 实现了对大量图书进行管理的目的。

4\. 对spring\-web，mybatis\-plus，druid，JWT进行了完整正确的整合。

2、功能实现或实现思想。

  1\. 使用lomlok，极大的简化了bean和java实体类的书写。

  2\. 使用mybatis\-plus而非mybatis，简化数据映射层的书写以及对数据库进行CRUD操作的简化。

  3\. 使用springboot内置的tomcat服务器进行图形化界面的渲染，采用vue和element\-ui进行前端的书写。

  4\. 采用JWT进行安全认证

3、如果项目的功能有不足之处，请加以列出。

  1\. 由于无法保存从后端的localhost/login获取的token，没有实现前端的登录验证。

  2\. Result的设计不够完美，没有接受http\-code的变量。

  3\. vue的拦截器没有实现。
