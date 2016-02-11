<#import "/spring.ftl" as spring/>
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

</body>

</html>
</#escape>