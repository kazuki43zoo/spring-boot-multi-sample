<#import "/spring.ftl" as spring/>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<#escape x as x?html>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Todo Detail</title>
    <link rel="stylesheet" href="<@spring.url '/css/styles.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
</head>

<body>

<div class="container">

    <h1>Todo Detail</h1>


    <div id="todoDetail">
        <div>
            <span>Title</span>:<span>${todo.todoTitle}</span>
        </div>
        <div>
            <span>finished</span>:<span>${todo.finished?string('Yes','No')}</span>
        </div>
        <div>
            <span>Deadline Date</span>:<span>${todo.deadlineDate!'-'}</span>
        </div>
        <div>
            <span>Create Date</span>:<span>${todo.createdAt}</span>
        </div>
        <#if !todo.finished>
            <form action="<@spring.url '/todos/${todo.todoId}'/>" method="post" style="display: inline-block;">
                <button name="finish" class="btn btn-default">Finish</button>
                <@sec.csrfInput />
            </form>
        </#if>
        <form action="<@spring.url '/todos/${todo.todoId}'/>" method="post" style="display: inline-block;">
            <button name="delete" class="btn btn-default">Delete</button>
            <@sec.csrfInput />
        </form>

    </div>

    <hr/>

    <a href="<@spring.url '/'/>" class="btn btn-default">Home</a>
    <a href="<@spring.url '/todos'/>" class="btn btn-default">Todo List</a>

</div>

</body>

</html>
</#escape>