# spring-boot-multi-sample
Spring Boot Multi Project Samples

[![Build Status](https://travis-ci.org/kazuki43zoo/spring-boot-multi-sample.svg?branch=master)](https://travis-ci.org/kazuki43zoo/spring-boot-multi-sample)


## Overview

Structure of this applications are following.

![Sample Applications Structure](overview.png)

> Note: **Other modules and directories**
>
> * `component`, `component-web`, `domain` and `screen` modules are not Spring Boot Application. These modules provided common components to the Spring Boot Applications.
> * `service-conf` provide a conf file for OS Service such as `init.d`.
> * `www` provide web content files for a Web Server such as Apache.

## Getting Started

### Get project

If you are git user, clone this project using `git clone` command.

```bash
$ git clone https://github.com/kazuki43zoo/spring-boot-multi-sample.git
```

If you are not git user, download zip file form [here](https://github.com/kazuki43zoo/spring-boot-multi-sample/archive/master.zip) and expand to any directory.

### Build project

Build project using maven(`./mvnw`) command.

```bash
$ cd spring-boot-multi-sample
$ ./mvnw install
```

### Run applications

Run applications as background process using `java -jar` commands.
(or run application as foreground process on different console without `&`)

```bash
$ java -jar database/target/database-1.0.0-SNAPSHOT.jar &
$ java -jar api-auth/target/api-auth-1.0.0-SNAPSHOT.jar &
$ java -jar api-resource/target/api-resource-1.0.0-SNAPSHOT.jar &
$ java -jar api-client/target/api-client-1.0.0-SNAPSHOT.jar &
$ java -jar screen-thymeleaf/target/screen-thymeleaf-1.0.0-SNAPSHOT.jar &
$ java -jar screen-freemarker/target/screen-freemarker-1.0.0-SNAPSHOT.jar &
$ java -jar screen-jsp/target/screen-jsp-1.0.0-SNAPSHOT.war &
```

or 

Run application as background process using `spring-boot:run` of `spring-boot-maven-plugin`.

```bash
$ ./mvnw -f database/pom.xml spring-boot:run &
$ ./mvnw -f api-auth/pom.xml spring-boot:run &
$ ./mvnw -f api-resource/pom.xml spring-boot:run &
$ ./mvnw -f api-client/pom.xml spring-boot:run &
$ ./mvnw -f screen-thymeleaf/pom.xml spring-boot:run &
$ ./mvnw -f screen-freemarker/pom.xml spring-boot:run &
$ ./mvnw -f screen-jsp/pom.xml spring-boot:run &
```

### Access to applications

URLs refer to [Modules & Context Paths](#modules--context-paths).

If you need login, you can use embedded user as follows.

| Username | Password | ROLE |
| -------- | -------- | ---- |
| demo | demo | USER |
| admin | password | ADMIN<br>USER |
| user | password | USER |
| kazuki43zoo | password | USER |


### Stop applications

Move to current job using `bg` command and stop job using "Control + C" for all application.

```bash
$ fg
./mvnw -f screen-jsp/pom.xml spring-boot:run
(Type "Control + C")
```


## Modules & Context Paths



| Type | Module | Context Path | Description | Remarks |
| :--: | :----- | ------------ | ----------- | ------- |
| API Server<br>(OAuth 2.0)  | api-auth          | /api-a | OAuth 2.0 Authorization Server (Resource Owner Authentication & Grant access authorities to resource)<br>[http://localhost:9080/api-a](http://localhost:9080/api-a) | |
|                            | api-resource      | /api-r | OAuth 2.0 Resource Server (RESTful Web Services)<br>[http://localhost:9081/api-r](http://localhost:9081/api-r) | |
|                            | api-client        | /api-c | OAuth 2.0 Client Application (Screen Application)<br>[http://localhost:9082/api-c](http://localhost:9082/api-c) | |
| Screen AP Server           | screen-thymeleaf  | /scr-t | Screen Application using Thymeleaf as view technology<br>[http://localhost:8081/scr-t](http://localhost:8081/scr-t) | |
|                            | screen-freemarker | /scr-f | Screen Application using FreeMarker as view technology<br>[http://localhost:8082/scr-f](http://localhost:8082/scr-f) | |
|                            | screen-jsp        | /scr-j | Screen Application using JSP as view technology<br>[http://localhost:8083/scr-j](http://localhost:8083/scr-j) | |
| H2 Database<br>Web Console | database          | /db    | Console Application of H2 Database<br>[http://localhost:10000/db](http://localhost:10000/db) | |
| Domain<br>Components       | domain            | -      | Domain components (`@Service`, `@Repository`, Domain Objects, etc ...) | |
| Common<br>Components       | component         | -      | Common components | |
|                            | component-web     | -      | Common components that depend on web layer (`@WebFilter`, `@WebListener`, etc ...) | |
|                            | screen            | -      | Application layer components(`@Controller`, `@ControllerAdvice`, Form, etc ...) for screen-xxx modules ... | |


## Application endpoints

### OAuth 2.0 Authorization Server

| Endpoint | Description | Remarks |
| -------- | ----------- | ------- |
| /oauth/authorize | Authorization Page(Grant access authorities to the specifying resource) | |
| /oauth/token | API for obtaining the access token(JWT) | |

### OAuth 2.0 Resource Server (RESTful Web Services)

| Endpoint | Description | Remarks |
| -------- | ----------- | ------- |
| / | API for returning hello message | |
| /todos | APIs for todo resources | |
| /todos/{todoId} | APIs for specifying todo resource | |

### Screen AP (or Auth 2.0 Client)

| Endpoint | Description | Remarks |
| -------- | ----------- | ------- |
| / | Top Page | |
| /todos | Todo List Page | |
| /todos/{todoId} | Todo Detail Page | |
| /login | Login Page | |
| /logout | Logout Processing | |

### H2 Database Web Console

| Endpoint | Description | Remarks |
| -------- | ----------- | ------- |
| / | Redirect to H2 Console Page | |
| /h2-console | H2 Console Page | |


## Appendix

### Run on EC2 instance of AWS

This sample application has been published using [EC2 instance of AWS ](http://ec2-52-69-78-174.ap-northeast-1.compute.amazonaws.com/).

### How to release to EC2 instance of AWS

It used the [release-aws.sh](https://github.com/kazuki43zoo/spring-boot-multi-sample/blob/master/release-aws.sh).

> Note: **Preconditions**
>
> * Installed the Git
> * Installed the OpenJDK 8
> * Installed the Apache

#### How to install the Git

```bash
$ sudo yum -y install git
```

#### How to install the OpenJDK 8

```bash
$ sudo yum -y install java-1.8.0-openjdk-devel
$ sudo alternatives --config java
2 プログラムがあり 'java' を提供します。

  選択       コマンド
-----------------------------------------------
*+ 1           /usr/lib/jvm/jre-1.7.0-openjdk.x86_64/bin/java
   2           /usr/lib/jvm/jre-1.8.0-openjdk.x86_64/bin/java

Enter を押して現在の選択 [+] を保持するか、選択番号を入力します:2
$ java -version
openjdk version "1.8.0_71"
OpenJDK Runtime Environment (build 1.8.0_71-b15)
OpenJDK 64-Bit Server VM (build 25.71-b15, mixed mode)
```

#### How to install the Apache

```bash
$ sudo yum install httpd
$ sudo chkconfig httpd on
$ sudo service httpd start
```

#### How to manage a fully executable jar/war and configuration files

It managed in a `${HOME}/apps/${VERSION}` directory.

e.g.)
```bash
$ ls -l ${HOME}/apps/1.0.0-SNAPSHOT
合計 168768
-rw-rw-r-- 1 ec2-user ec2-user       19  2月 27 15:08 api-auth.conf
-r-x------ 1 ec2-user ec2-user 31405284  2月 27 15:08 api-auth.jar
-rw-rw-r-- 1 ec2-user ec2-user       19  2月 27 15:08 api-client.conf
-r-x------ 1 ec2-user ec2-user 28951479  2月 27 15:08 api-client.jar
-rw-rw-r-- 1 ec2-user ec2-user       19  2月 27 15:08 api-resource.conf
-r-x------ 1 ec2-user ec2-user 23847340  2月 27 15:08 api-resource.jar
-rw-rw-r-- 1 ec2-user ec2-user      146  2月 27 10:21 application-default.yml
-rw-rw-r-- 1 ec2-user ec2-user       19  2月 27 15:08 database.conf
-r-x------ 1 ec2-user ec2-user 15647599  2月 27 15:08 database.jar
-rw-rw-r-- 1 ec2-user ec2-user       19  2月 27 15:08 screen-freemarker.conf
-r-x------ 1 ec2-user ec2-user 22004744  2月 27 15:08 screen-freemarker.jar
-rw-rw-r-- 1 ec2-user ec2-user       19  2月 27 15:08 screen-jsp.conf
-r-x------ 1 ec2-user ec2-user 23785136  2月 27 15:08 screen-jsp.war
-rw-rw-r-- 1 ec2-user ec2-user       19  2月 27 15:08 screen-thymeleaf.conf
-r-x------ 1 ec2-user ec2-user 27123853  2月 27 15:08 screen-thymeleaf.jar
```

#### How to register a service
 
it used the `init.d`.

```bash
$ ls -l /etc/init.d/boot*
lrwxrwxrwx 1 root root 47  2月 27 15:08 /etc/init.d/boot-api-a -> /home/ec2-user/apps/1.0.0-SNAPSHOT/api-auth.jar
lrwxrwxrwx 1 root root 49  2月 27 15:08 /etc/init.d/boot-api-c -> /home/ec2-user/apps/1.0.0-SNAPSHOT/api-client.jar
lrwxrwxrwx 1 root root 51  2月 27 15:08 /etc/init.d/boot-api-r -> /home/ec2-user/apps/1.0.0-SNAPSHOT/api-resource.jar
lrwxrwxrwx 1 root root 47  2月 27 15:08 /etc/init.d/boot-db -> /home/ec2-user/apps/1.0.0-SNAPSHOT/database.jar
lrwxrwxrwx 1 root root 56  2月 27 15:08 /etc/init.d/boot-scr-f -> /home/ec2-user/apps/1.0.0-SNAPSHOT/screen-freemarker.jar
lrwxrwxrwx 1 root root 49  2月 27 15:08 /etc/init.d/boot-scr-j -> /home/ec2-user/apps/1.0.0-SNAPSHOT/screen-jsp.war
lrwxrwxrwx 1 root root 55  2月 27 15:08 /etc/init.d/boot-scr-t -> /home/ec2-user/apps/1.0.0-SNAPSHOT/screen-thymeleaf.jar
```

Register a service using following command.

```bash
$ sudo ln -f -s ${HOME}/apps/${VERSION}/{artifact} /etc/init.d/{service-name}
```

e.g)

```bash
$ sudo ln -f -s ${HOME}/apps/1.0.0-SNAPSHOT/database.jar /etc/init.d/boot-db
```

#### How to control a service manually

It use the `service` command.

```bash
$ sudo service {service-name} {option[start|stop|restart|force-reload|status|run]}
```

e.g)

```bash
$ sudo service boot-database start
```


#### How to startup a service by automatically

It used the `chkconfig` command.

```bash
$ sudo chkconfig --add {service-name}
```

e.g.)

```bash
$ sudo chkconfig --add boot-db
```

### Apache Reverse Proxy Settings

This sample application use a Apache Reverse Proxy on EC2 instance of AWS.

#### `/etc/httpd/conf/httpd.conf`

```conf
# ...

ProxyRequests Off

<Proxy *>
    Order deny,allow
    Allow from all
</Proxy>

ProxyPass /db http://localhost:10000/db
ProxyPassReverse /db http://localhost:10000/db

ProxyPass /api-a http://localhost:9080/api-a
ProxyPassReverse /api-a http://localhost:9080/api-a

ProxyPass /api-c http://localhost:9082/api-c
ProxyPassReverse /api-c http://localhost:9082/api-c

ProxyPass /scr-t http://localhost:8081/scr-t
ProxyPassReverse /scr-t http://localhost:8081/scr-t

ProxyPass /scr-f http://localhost:8082/scr-f
ProxyPassReverse /scr-f http://localhost:8082/scr-f

ProxyPass /scr-j http://localhost:8083/scr-j
ProxyPassReverse /scr-j http://localhost:8083/scr-j
```
