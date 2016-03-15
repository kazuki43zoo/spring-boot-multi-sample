<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Home</title>
    <link rel="stylesheet" href="<c:url value='/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" type="text/css"/>
    <spring:theme var="themeStyleSheet" code="styleSheet"/>
    <link rel="stylesheet" href="<c:url value='${themeStyleSheet}'/>" type="text/css"/>
</head>

<body>
<div class="container">

    <h1 id="message"><spring:message code="screen.welcome" text="Welcome !!"/></h1>

    <c:if test="${param.containsKey('logout')}">
        <div class="alert alert-success" role="alert">
            <span>Logout was succeed</span>
        </div>
    </c:if>

    <a href="<c:url value='/todos'/>" class="btn btn-default">Todo List</a>

    <hr/>
    <sec:authorize access="isAuthenticated()" var="isAuthenticated"/>
    <c:choose>
        <c:when test="${isAuthenticated}">
            <c:url value="/logout" var="logoutUrl"/>
            <form:form action="${logoutUrl}" cssStyle="display: inline-block;">
                <button class="btn btn-default">Logout</button>
            </form:form>
        </c:when>
        <c:otherwise>
            <a href="<c:url value='/login'/>" class="btn btn-default">Login</a>
        </c:otherwise>
    </c:choose>

</div>
</body>

</html>