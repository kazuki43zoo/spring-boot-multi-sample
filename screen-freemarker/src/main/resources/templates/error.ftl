<#import "/spring.ftl" as spring/>
<#escape x as x?html>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Error</title>
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<@spring.url '/css/styles.css'/>" type="text/css"/>
    <#assign themeStyleSheet><@spring.theme 'styleSheet'/></#assign>
    <link rel="stylesheet" href="<@spring.url '${themeStyleSheet}'/>" type="text/css"/>
</head>

<body>
<div class="container">
    <h1><@spring.message "screen.error"/></h1>
</div>
</body>

</html>
</#escape>