<#import "/spring.ftl" as spring/>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<#escape x as x?html>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><@spring.message "screen.login"/></title>
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<@spring.url '/css/styles.css'/>" type="text/css"/>
    <#assign themeStyleSheet><@spring.theme 'styleSheet'/></#assign>
    <link rel="stylesheet" href="<@spring.url '${themeStyleSheet}'/>" type="text/css"/>
</head>

<body>

<div class="container">

    <h1 id="message"><@spring.message "screen.login"/></h1>

    <#if RequestParameters.error??>
        <div class="alert alert-danger" role="alert">
            <span>${SPRING_SECURITY_LAST_EXCEPTION.localizedMessage}</span>
        </div>
    </#if>
    <#if RequestParameters.logout??>
        <div class="alert alert-success" role="alert">
            <span>Logout was succeed</span>
        </div>
    </#if>

    <form action="<@spring.url '/login'/>" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" name="username" id="username" class="form-control" placeholder="Username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" id="password" class="form-control" placeholder="Password">
        </div>
        <button class="btn btn-default">Login</button>
        <@sec.csrfInput />
    </form>

    <hr/>

    <a href="<@spring.url '/'/>" class="btn btn-default">Top</a>

</div>
</body>

</html>
</#escape>