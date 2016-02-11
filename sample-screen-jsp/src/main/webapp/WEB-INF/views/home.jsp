<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="<c:url value='/styles.css'/>" type="text/css">
</head>

<body>
<span id="message">
<spring:message code="screen.welcome" text="Welcome !!"/>
</span>
<hr>
<a href="<c:url value='/todos'/>">Todo List</a>

</body>

</html>