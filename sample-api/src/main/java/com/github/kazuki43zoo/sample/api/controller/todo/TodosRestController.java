package com.github.kazuki43zoo.sample.api.controller.todo;


import com.github.kazuki43zoo.sample.domain.model.Todo;
import com.github.kazuki43zoo.sample.domain.service.TodoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.relativeTo;

@RestController
@RequestMapping("todos")
public class TodosRestController {

    @Autowired
    TodoService todoService;

    @RequestMapping(path = "{todoId}", method = RequestMethod.GET)
    public TodoResource getTodo(@PathVariable String todoId) {

        Todo todo = todoService.findOne(todoId);

        TodoResource resource = new TodoResource();
        BeanUtils.copyProperties(todo, resource);

        return resource;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createTodo(
            @Validated @RequestBody TodoResource newResource,
            UriComponentsBuilder uriBuilder) {

        Todo newTodo = new Todo();
        BeanUtils.copyProperties(newResource, newTodo);

        Todo createdTodo = todoService.create(newTodo);

        URI resourceUri = relativeTo(uriBuilder)
                .withMethodCall(on(TodosRestController.class).getTodo(createdTodo.getTodoId()))
                .build().encode().toUri();

        return ResponseEntity
                .created(resourceUri).build();
    }

    @RequestMapping(path = "{todoId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@PathVariable String todoId,
                    @Validated @RequestBody TodoResource resource) {

        Todo updatingTodo = new Todo();
        BeanUtils.copyProperties(resource, updatingTodo);

        todoService.update(updatingTodo);

    }

    @RequestMapping(path = "{todoId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String todoId) {
        todoService.delete(todoId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TodoResource> searchTodos() {

        List<TodoResource> resources = new ArrayList<>();
        todoService.findAll().forEach(todo -> {
            TodoResource resource = new TodoResource();
            BeanUtils.copyProperties(todo, resource);
            resources.add(resource);
        });

        return resources;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooks() {
        todoService.findAll()
                .forEach(todo -> todoService.delete(todo.getTodoId()));
    }

}
