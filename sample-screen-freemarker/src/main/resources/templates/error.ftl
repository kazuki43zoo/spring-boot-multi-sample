<#import "/spring.ftl" as spring/>
<#escape x as x?html>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Error</title>
    <link rel="stylesheet" href="<@spring.url '/styles.css'/>" type="text/css"/>
</head>

<body>
<span><@spring.message "screen.error"/></span>
</body>

</html>
</#escape>