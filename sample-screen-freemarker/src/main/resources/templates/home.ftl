<#import "/spring.ftl" as spring/>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<#escape x as x?html>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Home</title>
    <link rel="stylesheet" href="<@spring.url '/styles.css'/>" type="text/css"/>
</head>

<body>
<span id="message"><@spring.message "screen.welcome"/></span>

<hr/>

<a href="<@spring.url '/todos'/>">Todo List</a>

<hr/>

    <@sec.authorize access="isAuthenticated()" var="isAuthenticated" />
    <#switch isAuthenticated>
        <#case true>
            <form action="<@spring.url '/logout'/>" method="post" style="display: inline-block;">
                <button>Logout</button>
                <@sec.csrfInput />
            </form>
            <#break />
        <#default>
            <a href="<@spring.url '/login'/>">Login</a>
    </#switch>

</body>

</html>
</#escape>