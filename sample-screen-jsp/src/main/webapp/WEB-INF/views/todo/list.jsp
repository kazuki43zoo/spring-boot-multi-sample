<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Todo List</title>
    <link rel="stylesheet" href="<c:url value='/styles.css'/>" type="text/css">
</head>
<body>
<h1>Todo List</h1>

<div id="todoForm">
    <c:url value="/todos" var="todosUrl"/>
    <form:form action="${todosUrl}" modelAttribute="todoForm">
        <form:input path="todoTitle"/>
        <form:errors path="todoTitle" cssClass="text-error"/>
        <form:button>Create Todo</form:button>
    </form:form>
</div>

<c:if test="${not empty todos}">

    <div id="todoList">
        <hr/>
        <ul>
            <c:forEach items="${todos}" var="todo">
                <li>
                    <c:choose>
                        <c:when test="${todo.finished}">
                            <span class="strike"><c:out value="${todo.todoTitle}"/></span>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${todo.todoTitle}"/>
                            <form:form action="${todosUrl}/${todo.todoId}" cssStyle="display: inline-block;">
                                <button name="finish">Finish</button>
                            </form:form>
                        </c:otherwise>
                    </c:choose>
                    <form:form action="${todosUrl}/${todo.todoId}" cssStyle="display: inline-block;">
                        <button name="delete">Delete</button>
                    </form:form>
                </li>
            </c:forEach>
        </ul>
    </div>

</c:if>

<hr/>

<a href="<c:url value='/'/>">Home</a>
<sec:authorize access="isAuthenticated()" var="isAuthenticated"/>
<c:choose>
    <c:when test="${isAuthenticated}">
        <c:url value="/logout" var="logoutUrl"/>
        <form:form action="${logoutUrl}" cssStyle="display: inline-block;">
            <button>Logout</button>
        </form:form>
    </c:when>
    <c:otherwise>
        <a href="<c:url value='/login'/>">Login</a>
    </c:otherwise>
</c:choose>

</body>
</html>