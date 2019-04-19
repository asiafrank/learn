## DEMO 项目
主要集成：Spring Boot, Swagger, Ali EDAS SDK。该项目作为以后 Ali EDAS 微服务的基本模版。以 WAR 包形式打包，放在 Ali-Tomcat 上运行。
项目运行后，Swagger 文档见 `http://{ip}:{port}/doc.html`

如果并不需要提供 HTTP 接口的项目，请将 Swagger 依赖去除。

> 注：使用 Ali EDAS 的根本原因是想使用 Ali Pandora 的监控服务，方便 Ali 云后台监控定位问题，以及流量控制、灰度发布等功能。
> 虽然个人并不太喜欢 Ali 云的这种绑定式开发服务，但其对与企业开发管理来说，还是很有价值的。

## 针对 Ali-Tomcat 的开发环境配置说明

Ali-Tomcat 基于 Tomcat-7.0.59 版本魔改过来的，其依赖的 jar 包很旧。
该 DEMO 项目依赖的 Spring-boot 是 2.1.4.RELEASE 版本，基本以 Tomcat-8.x 以上版本作为依赖。
要想将 DEMO 项目运行在 Ali-Tomcat 上，需要做以下兼容：


- 将 Spring-Boot 依赖的 `javax.el-api-3.0.0.jar` 拷贝到 Ali-Tomcat lib 下，并将 `el-api.jar` 重命名为 `el-api.jarbak`
- spring-boot-start-test 依赖的某些包基于 Java9，务必注意不要将其打包到项目中。
- spring-boot 打包成 war 包，必须配置 ServletInitializer 来让 Tomcat 识别，否则打包过去也是"死"的，不是"活"的。
- IDEA 必须是 Ultimate 版本，才有 Tomcat-Server 的启动配置。 而且 Tomcat-Server 的 VM Option 需要指定 pandora 的路径即
```text
-Dpandora.location=/Users/asiafrank/programs/taobao-tomcat-7.0.59/deploy/taobao-hsf.sar
-DSERVER_IP=10.1.30.36
```

> 吐槽：Ali 的东西，命名都不统一，一会儿 taobao-Tomcat 一会儿 Ali-Tomcat；一会儿 Pandora，一会儿又 taobao-hsf

知道以上几点后，就可以按照 EDAS 文档来配置开发环境了。


- 1.[部署轻量配置中心](https://help.aliyun.com/document_detail/44163.html)
- 2.[安装 Ali-Tomcat 和 Pandora 配置开发环境](https://help.aliyun.com/document_detail/99410.html)

注意轻量配置中心很坑，必须使用 8080 和 9600 端口，而且强制要配置 jmenv.tbsite.net 的 hosts 映射。