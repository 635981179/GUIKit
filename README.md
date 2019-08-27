# GUIKit
使用JAVAFX为公司制作的可以查看项目信息、更新项目信息的小工具。
## 标签
- 多线程 
- JAVAFX
- GUI
- 爬虫
## 编程思路
和web思路相似的MVC结构，先绑定对象和组件。指令异步执行，对事件编程。
## 代码结构
- Controller 页面的控制器 页面的初始化、用户指令响应在这里完成
- dao 数据库访问逻辑
- domain 实体类Vo
- task 异步执行的任务 单例跟踪执行状态
- utils DateUtils 和 StringUtils封装了两个类的静态方法 JdbcUtils应该不陌生 里面会加载驱动 封装getConnection 和 release
- resource img存放页面要用的png pages内是fxml格式的 页面 和 html差不多 也可以加一些样式 这里只做了简单的颜色 和排版
- set.ini 存放数据库连接配置
![结构](https://github.com/635981179/GUIKit/blob/master/1.png)
## 功能
- table展示项目信息
- 可以爬取信息发布网站更新项目信息
