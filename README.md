# 聚合搜索中台项目

> 作者：[Name](https://github.com/Sonmenily)

基于 Java SpringBoot 项目初始模板，开发的聚合搜索中台项目

### 项目点

- hutool 组件库做 http请求 
- jsoup 做图片数据的抓取

### 使用手册

- 创建数据库连接
- 执行 sql 文件夹中的文件，创建项目运行所需表
- 修改 application.yml 文件中的数据源配置
- 开启 job/once 文件夹中的 [FetchInitPostList.java](src%2Fmain%2Fjava%2Fcom%2Fliujian%2Fspringbootinit%2Fjob%2Fonce%2FFetchInitPostList.java) 的 @Component 注解，具体作用在 todo 中标识
- 

正在开发中..........