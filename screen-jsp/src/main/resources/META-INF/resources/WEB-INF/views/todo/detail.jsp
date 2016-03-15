<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Todo Detail</title>
    <link rel="stylesheet" href="<c:url value='/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" type="text/css"/>
    <spring:theme var="themeStyleSheet" code="styleSheet"/>
    <link rel="stylesheet" href="<c:url value='${themeStyleSheet}'/>" type="text/css"/>
</head>
<body>

<div class="container">

    <h1>Todo Detail</h1>

    <div id="todoDetail" class="form-horizontal">
        <c:url value='/todos/${todo.todoId}' var="todoUrl"/>

        <div class="form-group">
            <label class="col-sm-2 control-label">Title</label>

            <div class="col-sm-10"><p class="form-control-static"><c:out value="${todo.todoTitle}"/></p></div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Finished</label>

            <div class="col-sm-10"><p class="form-control-static"><c:out value="${todo.finished ? 'Yes' : 'No'}"/></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Deadline Date</label>

            <div class="col-sm-10"><p class="form-control-static"><c:out
                    value="${not empty todo.deadlineDate ? todo.deadlineDate : '-'}"/></p></div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Create Date</label>

            <div class="col-sm-10"><p class="form-control-static"><c:out value="${todo.createdAt}"/></p></div>
        </div>
        <div class="form-group">
            <span class="col-sm-2"></span>

            <div class="col-sm-10">
                <c:if test="${not todo.finished}">
                    <form:form action="${todoUrl}" style="display: inline-block;">
                        <button name="finish" class="btn btn-default">Finish</button>
                    </form:form>
                </c:if>
                <form:form action="${todoUrl}" style="display: inline-block;">
                    <button name="delete" class="btn btn-default">Delete</button>
                </form:form>
            </div>
        </div>

    </div>

    <hr/>

    <a href="<c:url value='/'/>" class="btn btn-default">Home</a>
    <a href="<c:url value='/todos'/>" class="btn btn-default">Todo List</a>

</div>
</body>
</html>