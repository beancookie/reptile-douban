# 豆瓣爬虫

## 项目结构

├── main
│   ├── java
│   │   └── cn
│   │       └── lz
│   │           └── reptile
│   │               └── douban
│   │                   ├── config // 配置
│   │                   ├── core // 爬虫代码
│   │                   ├── entity
│   │                   ├── pipeline
│   │                   ├── repository
│   │                   └── scheduled // 定时任务
│   └── resources

## 相关技术
使用SpringBoot方便开发部署，Webmagic中使用Redis进行url去重，元素数据存放在ElasticSearch中，使用Kibana进行数据可视化
