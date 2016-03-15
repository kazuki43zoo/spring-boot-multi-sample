<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="<c:url value='/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" type="text/css"/>
    <spring:theme var="themeStyleSheet" code="styleSheet"/>
    <link rel="stylesheet" href="<c:url value='${themeStyleSheet}'/>" type="text/css"/>
</head>

<body>
<div class="container">
    <h1><spring:message code="screen.error" text="Error !!"/></h1>
</div>
</body>

</html>