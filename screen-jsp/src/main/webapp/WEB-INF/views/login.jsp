<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Login</title>
    <link rel="stylesheet" href="<c:url value='/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" type="text/css"/>
    <spring:theme var="themeStyleSheet" code="styleSheet"/>
    <link rel="stylesheet" href="<c:url value='${themeStyleSheet}'/>" type="text/css"/>
</head>

<body>
<div class="container">

    <h1 id="message"><spring:message code="screen.login" text="Login Page !!"/></h1>

    <c:if test="${param.containsKey('error')}">
        <div class="alert alert-danger" role="alert">
            <span><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.localizedMessage}"/></span>
        </div>
    </c:if>
    <c:if test="${param.containsKey('logout')}">
        <div class="alert alert-success" role="alert">
            <span>Logout was succeed</span>
        </div>
    </c:if>

    <c:url value="/login" var="loginUrl"/>
    <form:form action="${loginUrl}">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" name="username" id="username" class="form-control" placeholder="Username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" id="password" class="form-control" placeholder="Password">
        </div>
        <button class="btn btn-default">Login</button>
    </form:form>

    <hr/>

    <a href="<c:url value='/'/>" class="btn btn-default">Top</a>

</div>
</body>

</html>