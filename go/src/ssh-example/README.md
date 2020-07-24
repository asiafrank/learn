尝试写一个ssh登录，以及文件上传的例子。

## 配置文件
```yaml
# scp-example.yaml
host: 127.0.0.1
port: 22
user: root
# 不需要密码，注释即可
password: root

# 如果需要通过私钥登录则声明
# 不需要则注释即可
identify:
  filePath: "~/.ssh/id_rsa"
  passphrase: root

# 如果不需要跳板机，则注释
jumpServer:
  host: 127.0.0.2
  port: 6000
  user: root2
  password: root2
  # 跳板机也可能需要私钥登录
  identify:
    filePath: "~/.ssh/id_rsa2"
    passphrase: root2

# scp 传文件配置
scp:
  localFilePath: /root/hello.txt
  destFilePath: /root/hello2.txt
  permission: 0600
```

## 执行
```shell script
ssh-normal -c scp-example.yaml
```

## 参考
- [Sending file over SSH in go](https://stackoverflow.com/questions/53256373/sending-file-over-ssh-in-go?noredirect=1&lq=1)
- [go-scp](https://github.com/bramvdbogaerde/go-scp)