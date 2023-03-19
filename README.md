# Blog-Project
基于Springboot + Vue3 开发的前后端分离博客

## 在线地址

**前台链接：** [duyuheng.cn:8080](http://duyuheng.cn:8080)

**后台链接：** [duyuheng.cn:8081](http://duyuheng.cn:8081)

**普通用户 账号：** guest，**密码**：123456

**普通管理员 账号：** admin，**密码**：123456

**友链管理员 账号：** link，**密码**：123456

**超级管理员 账号：** dyh，**密码**：123456

**Github 地址：** [https://github.com/mydddyh/Blog](https://github.com/mydddyh/Blog)

**前台接口文档：** [http://duyuheng.cn:8082/swagger-ui.html](http://duyuheng.cn:8082/swagger-ui.html)

**后台接口文档：** [http://duyuheng.cn:8083/swagger-ui.html](http://duyuheng.cn:8083/swagger-ui.html)

## 本地运行

1. 修改后端yml配置文件中的数据库Mysql、对象存储OSS等连接信息
2. SQL 文件位于根目录下的`blog.sql`，将其中的数据导入到自己本地数据库中
3. 前端文件位于根目录下的`vue.zip`
4. 项目启动后，使用`dyh`超级管理员账号登录后台，密码为`123456`

## 项目特点

- 含有最新评论、文章目录、文章推荐和文章置顶功能
- 文章编辑使用 Markdown 编辑器
- 发布评论、回复评论、表情包
- 采用 RBAC 权限模型，使用 Spring Security 进行权限管理
- 支持动态权限修改、动态菜单和路由
- 实现日志管理、定时任务管理
- 采用 Restful 风格的 API，注释完善

## 文件结构

- blog-framework: 前后台共用,
  - domain: 实体类和视图封装
  - mapper: 数据库处理
  - service: 业务逻辑处理
    - impl: 主要功能的实现
  - annotation & aspect: 自定义统计注解
  - config: 配置类
  - constants: 常量
  - exception: 自定义异常
  - handler: 异常处理和字段填充
  - utils: 工具类
- blog-web: 前台工程
  - controller: 前台控制器
- blog-admin: 后台工程
  - controller: 后台控制器

## 技术介绍

SpringBoot + Mysql + MybatisPlus + SpringSecurity + Redis + Swagger2 + Nginx

## 运行环境

**服务器：** 阿里云 2 核 2G Ubuntu 20.04.5

**对象存储：** 七牛云 OSS

## 开发环境

| 开发工具              | 说明               |
| --------------------- | ------------------ |
| IDEA                  | Java 开发工具 IDE  |
| VS Code               | Vue 开发工具 IDE   |
| MySQL Workbench       | MySQL 远程连接工具 |
| FinalShell            | Linux 远程连接工具 |

| 开发环境      | 版本   |
| ------------- | ------ |
| OpenJDK       | 11     |
| MySQL         | 8.0.32 |
| Redis         | 5.0.7  |

## 项目截图

![](http://rr1o9xquo.bkt.clouddn.com/%E5%8D%9A%E5%AE%A2%E9%A1%B5%E9%9D%A21.jpg)
![](http://rr1o9xquo.bkt.clouddn.com/%E5%8D%9A%E5%AE%A2%E9%A1%B5%E9%9D%A22.jpg)

特别鸣谢：

- [ 三更草堂 ](https://www.bilibili.com/video/BV1hq4y1F7zk)