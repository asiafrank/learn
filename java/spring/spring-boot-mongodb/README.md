# learn spring boot with mongodb
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

### build and start using gradle
```shell
$ gradle bootRun
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

## Reference
[Spring Boot Reference Guide](http://docs.spring.io/autorepo/docs/spring-boot/current/reference/htmlsingle/)