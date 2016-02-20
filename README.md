# spring-boot-multi-sample
Spring Boot Multi Project Samples

[![Build Status](https://travis-ci.org/kazuki43zoo/spring-boot-multi-sample.svg?branch=master)](https://travis-ci.org/kazuki43zoo/spring-boot-multi-sample)


## Overview

Structure of this applications are following.

![alt text](overview.png "Sample Applications Structure")

> Note:
>
> `component`, `component-web`, `domain` and `screen` modules are not Spring Boot Application. These modules provided common components to the Spring Boot Applications.

## Modules & Context Paths
| Type | Module | Context Path | Description |
| :--: | :--------- | ------------ | --- | ------------ |
| Screen AP Server          | screen-thymeleaf  | /scr-t | Screen Application using Thymeleaf as view technology<br>[http://localhost:8081/scr-t](http://localhost:8081/scr-t) |
|                           | screen-freemarker | /scr-f | Screen Application using FreeMarker as view technology<br>[http://localhost:8082/scr-f](http://localhost:8082/scr-f) |
|                           | screen-jsp        | /scr-j | Screen Application using JSP as view technology<br>[http://localhost:8083/scr-j](http://localhost:8083/scr-j) |
| API Server<br>(OAuth 2.0) | api-client        | /api-c | OAuth 2.0 Client Application (Screen Application)<br>[http://localhost:9082/api-c](http://localhost:9082/api-c) |
|                           | api-resource      | /api-r | OAuth 2.0 Resource Server (RESTful Web Services)<br>[http://localhost:9081/api-r](http://localhost:9081/api-r) |
|                           | api-auth          | /api-a | OAuth 2.0 Authorization Server (Resource Owner Authentication & Grant access authorities to resource)<br>[http://localhost:9080/api-a](http://localhost:9080/api-a) |
| Database<br>Console AP    | database          | /db    | Console Application of H2 Database<br>[http://localhost:10000/db](http://localhost:10000/db) |
| Domain<br>Components      | domain            | -      | Domain components (`@Service`, `@Repository`, Domain Objects, etc ...) |
| Common<br>Components      | component         | -      | Common components |
|                           | component-web     | -      | Common components that depend on web layer (`@WebFilter`, `@WebListener`, etc ...) |
|                           | screen            | -      | Application layer components(`@Controller`, `@ControllerAdvice`, Form, etc ...) for screen-xxx modules ... |
