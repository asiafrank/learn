# Learn spring boot with postgresql
It's an example of `spring-boot` with `jpa` using `PostgreSQL`, also include `redis` and `cache`.

# Run the example with steps below 
(`$` is Terminal, `%` is psql)

Start redis with config.
```shell
$ redis-server redis.conf
```
If you have not installed `redis` yet, go to [redis.io](https://redis.io/download) and
download a suitable version of redis(I use 3.0.7).

Start PostgreSQL for `Mac OS X`.
```shell
$ g_ctl -D /usr/local/var/postgres start
```
If you use other OS, please read [PostgreSQL server start](https://www.postgresql.org/docs/9.6/static/server-start.html).

Then create your database through `default_username`, generally `postgres`.
```shell
$ createdb -O default_username -E UTF8 -U default_username -e db_name;
```

Login `psql`.
```shell
$ psql -d db_name -U default_username
```

Create another user for the project (Don't forget `;`).
```shell
% create user your_username with login;
% \password your_username;
```
Then use created user above to login through `psql`.
```shell
$ psql -d db_name -U your_username
```
Execute sql one by one from `src/main/resources/example.sql` in psql
or use `psql -d db_name -U default_username -f sql_file` in terminal.

There is some useful references, help you to use PostgreSQL correctly:
- [Create user](https://www.postgresql.org/docs/9.6/static/sql-createuser.html)
- [Modify pg_hba.conf](https://www.postgresql.org/docs/9.6/static/auth-pg-hba-conf.html)

Now config application.properties. If you have problem, try to read [Common application properties](http://docs.spring.io/autorepo/docs/spring-boot/current/reference/htmlsingle/#common-application-properties).

Finally Build and start the project (of cause install `jdk` and `maven` first).
```shell
$ mvn spring-boot:run
```

### Send some request
Delete
```shell
DELETE http://localhost:8080
```

Create
```shell
POST http://localhost:8080
```

Get
```shell
GET http://localhost:8080
```

## Reference
[Spring Boot Reference Guide](http://docs.spring.io/autorepo/docs/spring-boot/current/reference/htmlsingle/)
