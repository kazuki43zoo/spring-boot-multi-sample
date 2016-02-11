package com.github.kazuki43zoo.sample.domain.service;

import com.github.kazuki43zoo.sample.domain.model.Todo;
import com.github.kazuki43zoo.sample.domain.repository.TodoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired(required = false)
    IdGenerator idGenerator = new JdkIdGenerator();

    @Autowired(required = false)
    Clock clock = Clock.systemDefaultZone();

    public Todo findOne(String todoId) {
        Todo todo = todoRepository.findOne(todoId);
        if (todo == null) {
            throw new TodoNotFoundException(todoId);
        }
        return todo;
    }

    public Collection<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo create(Todo todo, String username) {

        String todoId = idGenerator.generateId().toString();
        LocalDateTime createdAt = LocalDateTime.now(clock);

        todo.setTodoId(todoId);
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

    public Todo update(Todo updatingTodo) {
        Todo todo = findOne(updatingTodo.getTodoId());
        BeanUtils.copyProperties(updatingTodo, todo);
        todoRepository.update(todo);
        return todo;
    }

    public void delete(String todoId) {
        Todo todo = findOne(todoId);
        todoRepository.delete(todo);
    }

}
