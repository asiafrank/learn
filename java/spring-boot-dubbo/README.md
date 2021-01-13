## 启动实验

配置 mysql nacos 库里表
sql 文件见 github 项目的 conf/nacos-mysql.sql

配置 nacos application.properties
```properties
spring.datasource.platform=mysql

db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=nacos
db.password=youdontknow
```

启动nacos

```shell
sh startup.sh -m standalone
```

访问 nacos： http://localhost:8848/nacos

启动 provider 服务和 consumer
