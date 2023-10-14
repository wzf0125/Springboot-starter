# Spirngboot项目开发脚手架食用指南

@author wzf

@date 2023-09-16

## 脚手架简介

> 开发背景

对单体脚手架进行模块拆分，代码封装，配置抽取，使用gradle替换maven

原脚手架地址: https://github.com/wzf0125/springboot-archetype



>   脚手架模块依赖关系

![脚手架项目结构](https://974500760-1303995467.cos.ap-guangzhou.myqcloud.com/PicGo/202310100050940.png)

> 脚手架模块

- `Core`模块
  - `starter-base` 核心模块
    - [x] 所有starter的共用代码，通用工具封装
  - `starter-mybatis`
    - [x] mybatis-plus配置
  - `starter-redis`
    - [x] redisTemplate配置
    - [x] redisTemplate操作封装
    - [x] SpringCache封装
    - [x] redisson配置
  - `starter-web`
    - [x] MVC拦截器配置
    - [x] 拦截路径配置抽取
    - [x] 自定义权限拦截器
    - [x] 统一响应配置
    - [x] 全局异常拦截
  - `starter-auth`
    - [x] 权限拦截注解定义
    - [x] MD5加密工具封装
    - [x] Token工具封装
    - [ ] JWT工具封装
  - `starter-log`
    - [x] 日志切面
    - [x] 日志切面注解
    - [x] 日志配置抽取
- `Ext`模块
  - [x] swagger模块
    - [x] swagger配置
  - [x] mail邮件模块
  - [ ] 对象存储模块
- `Demo`业务模块
  - 功能测试
    - [x] 认证功能测试
    - [x] 邮件功能测试
    - [x] mybatis-plus功能测试
    - [ ] spring cache功能测试



## 模块解析





## Gradle配置解析





## 项目构建





