# Spirngboot项目开发脚手架食用指南

@author wzf

@date 2023-09-16

## 脚手架简介

> 开发背景

对单体脚手架进行模块拆分，代码封装，配置抽取，使用gradle替换maven

将各核心功能配置(mybatis-plus,redis,spring-web等)拆分成独立模块，归属于核心core模块下，将拓展功能提取到ext模块下，以实现代码分离解耦的目的，
简化业务功能的代码和配置。

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
    - [X] 腾讯云COS
    - [X] 阿里云OOS
    - [ ] 亚马逊云AWS
    - [ ] minio
- `Demo`业务模块
  - 功能测试
    - [x] 认证功能测试
    - [x] 邮件功能测试
    - [x] mybatis-plus功能测试
    - [ ] spring cache功能测试



## 食用指南

### core模块

#### stater-base

- 核心模块
- 工具类
- 常量定义
- 共享依赖

#### starter-mybatis

- mybatis-plus配置

#### stater-redis

- redis配置
- redisTempalte封装
- SpringCache封装
- redisson封装

#### starter-web

- webMVC配置
- webMVC配置抽取
  - 具体看WebMvcConfigProperties 
    - 抽取放行路径配置
    - 抽取拦截路径配置
    - 抽取资源路径映射配置
- 响应状态码定义
- 全局统一响应类
- 认证拦截器
- 全局异常拦截器
  - 拦截各类异常
  - 自定义业务异常
- 系统配置抽取
  - debug模式开关(全局异常拦截是否返回异常信息)

#### starter-log

- 日志切面注解
- 日志切面
- 日志配置抽取

#### starter-auth

- 权限拦截注解
- Token工具封装
- 权限级别 (todo 重构为接口常量形式，而非枚举，抽离权限级别定义至通用模块(common))

### ext 拓展模块

#### swagger

- swagger + knif4j配置
  - swagger地址: http://127.0.0.1:8081/swagger-ui/index.html
  - knif4j地址: http://127.0.0.1:8081/doc.html (一般用这个)

#### mail

- 邮件功能封装
  - 责任链模式处理下发
  - 提供异步/同步功能支持
  - 提供

### common

- 通用模块

- BaseController
  - 附带日志切面注解，继承此类即可被日志切入
    - 默认Request Log + Response Log
  - Token增强，可通过getUid / getRole方法获取用户id/role权限
    - 用户信息在认证拦截器处放入
    - 需要其他信息可修改认证拦截器配置和BaseController代码

### demo

示例代码

- controller实现
- mapper
- service

### demo-api

- 实体类
- 常量
- dto
- vo
- controller接口



## 构建教程

构建插件`apply plugin: 'java-library'`

注意: 非构建模块引入此插件会出现自动创建src/main src/test模块

### 新模块

1. 在build.gradle中定义模块类型

```
ext {
    // 所有项目模块
    allProjectList = Arrays.asList(
            project(":core"),
            project(":ext"),
            project(":common"),
            project("demo"),
            project("demo-api")
    ).toSet()
    allProjectList.addAll(project(":core").subprojects)
    allProjectList.addAll(project(":ext").subprojects)

    // 空父工程
    pomProjectList = Arrays.asList(
            project(":core"),
            project(":ext")
    )

    // 需要打包的模块
    packageProjcetList = Arrays.asList(
            project(":demo")
    )
}
```

2. 在`settings.gradle`中注册该模块(若不存在父模块的情况)

```
rootProject.name = 'starter'

def moduleList = ['common', 'core', 'ext', 'demo', 'demo-api']
moduleList.forEach { module ->
    {
        include module
        file("${rootDir}/" + module).eachDirMatch(~/.*/) {
            include module + ":${it.name}"
        }
    }
}

```



打包成jar包使用gradle bootJar功能
