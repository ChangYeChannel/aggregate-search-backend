# 聚合搜索中台项目

> 作者：[Name](https://github.com/Sonmenily)

基于 Java SpringBoot 项目初始模板，开发的聚合搜索中台项目

### 项目点

- 门面模式（[SearchController.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fcontroller%2FSearchController.java) = 搜索接口）
- 适配器模式（[datasource](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fdatasource) = 系统提供后续需要接入的类别要实现的接口规范）
- 注册器模式（单例模式）（[DataSourceRegister.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fdatasource%2FDataSourceRegister.java) = 注册器会提前将所有的数据源装配好）
- Hibernate Validator进行参数正确性校验
- hutool 组件库做 http 请求 
- jsoup 做图片数据的抓取

### 使用手册

- 创建数据库连接
- 执行 sql 文件夹中的文件，创建项目运行所需表
- 修改 application.yml 文件中的数据源配置
- 开启 job/once 文件夹中的 [FetchInitPostList.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fjob%2Fonce%2FFetchInitPostList.java) 的 @Component 注解，具体作用在 todo 中标识
- 

正在开发中..........