<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Access Error</title>
    <link rel="stylesheet" href="<c:url value='/styles.css'/>" type="text/css">
</head>

<body>
<spring:message code="screen.accessError" text="Access Error !!"/>
</body>

</html>