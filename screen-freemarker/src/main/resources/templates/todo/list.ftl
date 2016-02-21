<#import "/spring.ftl" as spring/>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<#escape x as x?html>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Todo List</title>
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/3.3.6/css/bootstrap.css'/>" type="text/css"/>
    <link rel="stylesheet" href="<@spring.url '/css/styles.css'/>" type="text/css"/>
    <#assign themeStyleSheet><@spring.theme 'styleSheet'/></#assign>
    <link rel="stylesheet" href="<@spring.url '${themeStyleSheet}'/>" type="text/css"/>
</head>

<body>

<div class="container">
    <h1>Todo List</h1>

    <div id="todoForm">
        <form action="<@spring.url '/todos'/>" method="post" class="form-inline">
            <@spring.formInput 'todoForm.todoTitle' 'class="form-control"' />
            <@spring.showErrors 'br', 'text-error'/>
            <button class="btn btn-default">Create</button>
            <@sec.csrfInput />
        </form>
    </div>

    <#if todos?size != 0>


        <table id="todoList" class="table table-hover">

            <tr>
                <th>#</th>
                <th>Title</th>
                <th>Operations</th>
            </tr>

            <#list todos as todo>
                <tr>
                    <td>${todo?counter}</td>
                    <td>
                        <#switch todo.finished>
                            <#case true>
                                <span class="strike"><a
                                        href="<@spring.url '/todos/${todo.todoId}'/>">${todo.todoTitle}</a></span>
                                <#break />
                            <#default>
                                <span><a href="<@spring.url '/todos/${todo.todoId}'/>">${todo.todoTitle}</a></span>
                        </#switch>
                    </td>
                    <td>
                        <#if !todo.finished>
                            <form action="<@spring.url '/todos/${todo.todoId}'/>" method="post"
                                  style="display: inline-block;">
                                <button name="finish" class="btn btn-default">Finish</button>
                                <@sec.csrfInput />
                            </form>
                        </#if>
                        <form action="<@spring.url '/todos/${todo.todoId}'/>" method="post"
                              style="display: inline-block;">
                            <button name="delete" class="btn btn-default">Delete</button>
                            <@sec.csrfInput />
                        </form>
                    </td>
                </tr>
            </#list>

        </table>

    </#if>

    <hr/>

    <a href="<@spring.url '/'/>" class="btn btn-default">Home</a>

    <@sec.authorize access="isAuthenticated()" var="isAuthenticated" />
    <#switch isAuthenticated>
        <#case true>
            <form action="<@spring.url '/logout'/>" method="post" style="display: inline-block;">
                <button class="btn btn-default">Logout</button>
                <@sec.csrfInput />
            </form>
            <#break />
        <#default>
            <a href="<@spring.url '/login'/>" class="btn btn-default">Login</a>
    </#switch>
</div>
</body>

</html>
</#escape>