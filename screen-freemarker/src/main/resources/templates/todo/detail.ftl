<#import "/spring.ftl" as spring/>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<#escape x as x?html>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Todo Detail</title>
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<@spring.url '/css/styles.css'/>" type="text/css"/>
    <#assign themeStyleSheet><@spring.theme 'styleSheet'/></#assign>
    <link rel="stylesheet" href="<@spring.url '${themeStyleSheet}'/>" type="text/css"/>
</head>

<body>

<div class="container">

    <h1>Todo Detail</h1>


    <div id="todoDetail" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">Title</label>

            <div class="col-sm-10"><p class="form-control-static">${todo.todoTitle}</p></div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Finished</label>

            <div class="col-sm-10"><p class="form-control-static">${todo.finished?string('Yes','No')}</p></div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Deadline Date</label>

            <div class="col-sm-10"><p class="form-control-static">${todo.deadlineDate!'-'}</p></div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Create Date</label>

            <div class="col-sm-10"><p class="form-control-static">${todo.createdAt}</p></div>
        </div>
        <div class="form-group">
            <span class="col-sm-2"></span>

            <div class="col-sm-10">
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
        </div>
    </div>

    <hr/>

    <a href="<@spring.url '/'/>" class="btn btn-default">Home</a>
    <a href="<@spring.url '/todos'/>" class="btn btn-default">Todo List</a>

</div>

</body>

</html>
</#escape>