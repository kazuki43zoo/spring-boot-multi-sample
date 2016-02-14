package com.github.kazuki43zoo.sample.domain.service;

import com.github.kazuki43zoo.sample.domain.model.Todo;
import com.github.kazuki43zoo.sample.domain.repository.TodoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired(required = false)
    Clock clock = Clock.systemDefaultZone();

    public Todo findOne(String todoId) {
        Todo todo = todoRepository.findOne(todoId);
        if (todo == null) {
            throw new TodoNotFoundException(todoId);
        }
        return todo;
    }

    public List<Todo> findAll(String username) {
        return todoRepository.findAll(username);
    }

    public Todo create(Todo todo, String username) {
        LocalDateTime createdAt = LocalDateTime.now(clock);
        todo.setUsername(username);
        todo.setCreatedAt(createdAt);
        todo.setFinished(false);
        todoRepository.create(todo);
        return todo;
    }

    public Todo finish(String todoId) {
        Todo todo = findOne(todoId);
        todo.setFinished(true);
        todoRepository.update(todo);
        return todo;
    }

    public Todo update(String todoId, Todo updatingTodo) {
        Todo todo = findOne(todoId);
        BeanUtils.copyProperties(updatingTodo, todo, "todoId");
        todoRepository.update(todo);
        return todo;
    }

    public void delete(String todoId) {
        Todo todo = findOne(todoId);
        todoRepository.delete(todo);
    }

}
