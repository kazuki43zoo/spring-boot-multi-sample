<#import "/spring.ftl" as spring/>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />
<#escape x as x?html>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Todo List</title>
    <link rel="stylesheet" href="<@spring.url '/styles.css'/>" type="text/css"/>
</head>

<body>

<h1>Todo List</h1>

<div id="todoForm">
    <form action="<@spring.url '/todos'/>" method="post">
        <@spring.formInput 'todoForm.todoTitle'/>
        <@spring.showErrors 'br', 'text-error'/>
        <button>Create Todo</button>
        <@sec.csrfInput />
    </form>
</div>

    <#if todos?size != 0>

    <div id="todoList">
        <hr/>
        <ul>
            <#list todos as todo>
                <li>
                    <#switch todo.finished>
                        <#case true>
                            <span class="strike">${todo.todoTitle}</span>
                            <#break />
                        <#default>
                            <span>${todo.todoTitle}</span>

                            <form action="<@spring.url '/todos/${todo.todoId}'/>" method="post"
                                  style="display: inline-block;">
                                <button name="finish">Finish</button>
                                <@sec.csrfInput />
                            </form>
                    </#switch>
                    <form action="<@spring.url '/todos/${todo.todoId}'/>" method="post" style="display: inline-block;">
                        <button name="delete">Delete</button>
                        <@sec.csrfInput />
                    </form>
                </li>
            </#list>
        </ul>
    </div>

    </#if>

<hr/>

<a href="<@spring.url '/'/>">Home</a>

    <@sec.authorize access="isAuthenticated()" var="isAuthenticated" />
    <#switch isAuthenticated>
        <#case true>
        <form action="<@spring.url '/logout'/>" method="post" style="display: inline-block;">
            <button>Logout</button>
            <@sec.csrfInput />
        </form>
            <#break />
        <#default>
        <a href="<@spring.url '/login'/>">Login</a>
    </#switch>

</body>

</html>
</#escape>