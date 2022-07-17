#  Code-Generate
## 简介

docker run --name dbase -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql
docker exec -it dddf2378853e env LANG=C.UTF-8 /bin/bash

## 使用说明

该项目为普通Java项目，依赖的环境有：

- JDK
- MySQL
- Maven
- Lombok插件



|      可配置项       |          说明           |
| :-----------------: | :---------------------: |
|         ip          |         IP地址          |
|        port         |         端口号          |
|       driver        |          驱动           |
|      dataBase       |         数据库          |
|      encoding       |          编码           |
|      loginName      |      数据库用户名       |
|      passWord       |       数据库密码        |
|       include       | 包括哪些表，默认*; 全部 |
|     projectName     |        项目名称         |
|     packageName     |       自定义包名        |
|     authorName      |  作者名称（用作注释）   |
|      rootPath       |        输出路径         |
| customHandleInclude | 自定义模板，默认*;全部  |



```Main方法 @see App.java```

```java
public class App {
    /***
     * 执行 - 构建项目
     */
    public static void main(String[] args){
        AbstractEngine engine = AbstractEngine.init();
        engine.execute();
    }
}
```

> 生成的结果是一个Maven项目，直接用IDE打开即可



## 技术细节

```本项目主要的核心即两个通过mysql内置的表字段查询配合FreeMaker模板，构建具有一定规律性，通用的代码内容```

- FreeMaker  DefaultEngine.java process方法
- mybatis 原生XML，包含增，批量增，删，批量删，多条件分页查询，列表查询，单一查询，单一数据修改等
- logback日志
- SpringBoot
- 拔插式拦截器（基于org.reflections实现），支持扫描指定接口



## 协议

### GNU General Public License v3.0



## 最后

该项目是从无到有一点点构建出来的，适合初学者和刚入门的人进行学习，更加适合大佬们直接快速构建简单Demo，把时间花在更有价值的事情上，欢迎各位Star & Fork.
