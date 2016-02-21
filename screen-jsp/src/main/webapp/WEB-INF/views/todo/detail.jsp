<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Todo Detail</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" type="text/css">
    <link rel="stylesheet" href="<c:url value='/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
</head>
<body>

<div class="container">

    <h1>Todo Detail</h1>

    <div id="todoDetail">
        <c:url value='/todos/${todo.todoId}' var="todoUrl"/>
        <div>
            <span>Title</span>:<span><c:out value="${todo.todoTitle}"/></span>
        </div>
        <div>
            <span>finished</span>:<span><c:out value="${todo.finished ? 'Yes' : 'No'}"/></span>
        </div>
        <div>
            <span>Deadline Date</span>:<span><c:out
                value="${not empty todo.deadlineDate ? todo.deadlineDate : '-'}"/></span>
        </div>
        <div>
            <span>Create Date</span>:<span><c:out value="${todo.createdAt}"/></span>
        </div>
        <c:if test="${not todo.finished}">
            <form:form action="${todoUrl}" style="display: inline-block;">
                <button name="finish" class="btn btn-default">Finish</button>
            </form:form>
        </c:if>
        <form:form action="${todoUrl}" style="display: inline-block;">
            <button name="delete" class="btn btn-default">Delete</button>
        </form:form>

    </div>

    <hr/>

    <a href="<c:url value='/'/>" class="btn btn-default">Home</a>
    <a href="<c:url value='/todos'/>" class="btn btn-default">Todo List</a>

</div>
</body>
</html>