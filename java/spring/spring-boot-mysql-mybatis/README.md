# Spring boot Sample
This project is using mybatis-starter, mysql, spring-boot-starter-jersey

# Run with maven
start server
```shell
cd sample-service
mvn spring-boot:run
```
send a request
```shell
GET http://localhost:8080/page
```

# Package and run
in `spring-boot-mysql-mybatis` project home
```shell
mvn package
```
after package, in `sample-service/target/sample-build` directory, execute `start.sh`
```shell
sh start.sh
```
send a request
```shell
GET http://localhost:8080/page
```

# Reference
[Spring boot](http://docs.spring.io/autorepo/docs/spring-boot/current/reference/htmlsingle/)