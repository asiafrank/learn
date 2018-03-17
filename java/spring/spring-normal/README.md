# learn spring in normal config
- spring-data-redis
- spring-data-cache
- spring-data-mongodb

### start redis
```shell
$ redis-server redis.conf
```

### start mongodb
```shell
$ mongodb --auth --dbpath=/data
```

### build using maven
```shell
$ mvn -Dmaven.test.skip=true clean install
```

### run with maven-tomcat-plugin
```shell
$ mvn tomcat7:run
```

### delete
Request
```shell
DELETE http://localhost:8080
```

### create
Request
```shell
POST http://localhost:8080
```

### get
Request
```shell
GET http://localhost:8080
```