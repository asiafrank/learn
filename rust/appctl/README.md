# rust 写的工具

- order_data_analyse: 分析订单的 csv 文件，并做对比
- appctl: java 项目启动及备份



## appctl

### 使用说明

设置读取的配置文件，执行完该命令后，以后都默认读取该配置文件
```shell script
appctl -f config.yml
```

查看当前使用的配置文件路径及一些版本信息
```shell script
appctl -v
```

备份项目文件, 使用日期号作为后缀来拷贝文件，如：demo.jar 会拷贝成 demo.jar_20200608
```shell script
appctl backup [app_name]
```
app_name 为 config.yml 中的配置

更新项目文件，从 config.yml `package_dir` 中获取新项目文件，迁移置 `app_file_path`
```shell script
appctl update [app_name]
```

启动项目，config.yml 中配置启动命令，appctl 去调用即可
```shell script
appctl start [app_name]
```

停止项目，根据 `app_name` 来查找 PID，然后 kill 它
```shell script
appctl stop [app_name]
```

部署项目，即备份->更新->停止->启动
```shell script
appctl deploy [app_name]
```

config.yml 例子:
```yaml

```