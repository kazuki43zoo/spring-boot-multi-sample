<#import "/spring.ftl" as spring/>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<#escape x as x?html>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Home</title>
    <link rel="stylesheet" href="<@spring.url '/css/styles.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
</head>

<body>

<div class="container">

    <h1 id="message"><@spring.message "screen.welcome"/></h1>

    <a href="<@spring.url '/todos'/>" class="btn btn-default">Todo List</a>

    <hr/>

    <@sec.authorize access="isAuthenticated()" var="isAuthenticated" />
    <#switch isAuthenticated>
        <#case true>
            <form action="<@spring.url '/logout'/>" method="post" style="display: inline-block;">
                <button class="btn btn-default">Logout</button>
                <@sec.csrfInput />
            </form>
            <#break />
        <#default>
            <a href="<@spring.url '/login'/>" class="btn btn-default">Login</a>
    </#switch>

</div>
</body>

</html>
</#escape>