# 聚合搜索项目

> 作者：[Name](https://github.com/Sonmenily)

基于 Java SpringBoot 项目初始模板，开发的聚合搜索中台项目

### 项目点

- 门面模式（[SearchController.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fcontroller%2FSearchController.java) = 搜索接口）
- 适配器模式（[datasource](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fdatasource) = 系统提供后续需要接入的类别要实现的接口规范）
- 注册器模式（单例模式）（[DataSourceRegister.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fdatasource%2FDataSourceRegister.java) = 注册器会提前将所有的数据源装配好）
- Hibernate Validator进行参数正确性校验
- hutool 组件库做 http 请求 
- jsoup 做图片数据的抓取
- ES 做文章数据搜索引擎
- 采用定时任务做 ES 的全量和增量同步

### 项目介绍

- 项目是一个聚合搜索平台，可以将多种不同类型，所属不同数据库中的数据，按照相同的搜索条件，查出每种类型符合条件的数据
- 项目体现了 搜索中台 的理念
- 项目提供user用户表、post文章表以及动态获取必应图片库图片做项目数据填充
- 采用 统一接口 + 门面模式 + 适配器模式 做不同数据的聚合索引
- 引入ES做post文章数据的搜索引擎，增强了对文章的搜索能力
- 统一接口开发，为后续平台接入新数据制定规范

### 使用手册

- 创建数据库连接和ES连接
- 在数据库访问工具中 执行 db 文件夹中的 .sql文件，创建项目运行所需表
- 在ES访问工具中 执行 db 文件夹中的 txt文件，创建项目运行所需索引
- 修改 application.yml 文件中的 MySQL 数据源配置
- 修改 application.yml 文件中的 ES 数据源配置
- 开启 job/once 文件夹中的 [FetchInitPostList.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fjob%2Fonce%2FFetchInitPostList.java) 的 @Component 注解
- [FetchInitPostList.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fjob%2Fonce%2FFetchInitPostList.java) 的作用是将编程导航网站的部分文章数据抓取到文章表中
- 开启 job/once 文件夹中的 [FullSyncPostToEs.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fjob%2Fonce%2FFullSyncPostToEs.java) 的 @Component 注解
- [FullSyncPostToEs.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fjob%2Fonce%2FFullSyncPostToEs.java) 的作用是将文章表中的数据全量同步到 ES 中
- 启动项目，查看控制台输出，确认正常连接到 MySQL、ES
- 待文章表中有数据并且ES中也有对应索引数据存在后、停止项目运行
- 将 job/once 文件夹中的两个类的 @Component 注解注释
- 开启 job/cycle 文件夹中的 [IncSyncPostToEs.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fjob%2Fcycle%2FIncSyncPostToEs.java) 的 @Component 注解
- [IncSyncPostToEs.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fjob%2Fcycle%2FIncSyncPostToEs.java) 的作用是异步定时任务，每隔一分钟会将前五分钟的数据库中文章表的更新后数据同步到 ES 中
- 至此，项目启动完成
- 项目集成knife4J -> 访问地址：协议://ip地址:端口号/api/doc.html



## 拓展知识点

- ES 的调用方式：HTTP请求（Restful API）、Kibana、客户端操作（java）
- ES 的java客户端调用方式：ES官方原生API、ES官方封装API（HighLevelRestClient）、Spring-Data系列框架（Spring-Data-Elasticsearch）
- ES 进行增量同步的方式：客户端定时任务、Mysql,Es双写、Logstash组件监听、Canal组件监听
