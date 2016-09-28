package com.github.kazuki43zoo.sample.api.controller.todo;

import com.github.kazuki43zoo.sample.component.tracking.TrackingId;
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
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.relativeTo;

@RestController
@RequestMapping("/todos")
public class TodosRestController {

    @Autowired
    TodoService todoService;

    @GetMapping
    public List<TodoResource> search(Principal principal) {
        return todoService.findAllByUsername(principal.getName())
                .stream().map(todo -> {
                    TodoResource resource = new TodoResource();
                    BeanUtils.copyProperties(todo, resource);
                    return resource;
                })
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Validated @RequestBody TodoResource newResource,
            UriComponentsBuilder uriBuilder,
            Principal principal, @TrackingId String trackingId) {
        Todo newTodo = new Todo();
        BeanUtils.copyProperties(newResource, newTodo);
        newTodo.setTrackingId(trackingId);
        Todo createdTodo = todoService.create(newTodo, principal.getName());
        URI resourceUri = relativeTo(uriBuilder)
                .withMethodCall(on(TodosRestController.class).get(createdTodo.getTodoId()))
                .build().encode().toUri();
        return ResponseEntity.created(resourceUri).build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(Principal principal) {
        todoService.findAllByUsername(principal.getName())
                .forEach(todo -> todoService.delete(todo.getTodoId()));
    }

    @GetMapping("{todoId}")
    public TodoResource get(@PathVariable String todoId) {
        Todo todo = todoService.findOne(todoId);
        TodoResource resource = new TodoResource();
        BeanUtils.copyProperties(todo, resource);
        return resource;
    }

    @PutMapping("{todoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@PathVariable String todoId,
                    @Validated @RequestBody TodoResource resource) {
        Todo updatingTodo = new Todo();
        BeanUtils.copyProperties(resource, updatingTodo);
        todoService.update(todoId, updatingTodo);
    }

    @DeleteMapping("{todoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String todoId) {
        todoService.delete(todoId);
    }

}
