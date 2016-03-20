package com.github.kazuki43zoo.sample.api.controller.todo;

import com.github.kazuki43zoo.sample.domain.model.Todo;
import com.github.kazuki43zoo.sample.domain.service.TodoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.UUID;

@Controller
public class TodosController {

    @Autowired
    TodoService todoService;

    @JmsListener(destination = "todo")
    public void create(TodoResource newResource
            , @Header(name = "username", defaultValue = "anonymousUser") String username
            , @Header(name = "trackingId", required = false) String trackingId
    ) {
        Todo newTodo = new Todo();
        BeanUtils.copyProperties(newResource, newTodo);
        newTodo.setTrackingId(Optional.ofNullable(trackingId).orElse(UUID.randomUUID().toString()));
        todoService.create(newTodo, username);
    }

}
