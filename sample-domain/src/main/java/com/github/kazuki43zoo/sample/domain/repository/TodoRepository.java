package com.github.kazuki43zoo.sample.domain.repository;

import com.github.kazuki43zoo.sample.domain.model.Todo;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.Collection;
import java.util.List;

public interface TodoRepository {

    @PostAuthorize("(returnObject == null) " +
            "or (returnObject.username == authentication.name)")
    Todo findOne(String todoId);

    List<Todo> findAll(String usename);

    void create(Todo todo);

    boolean update(Todo todo);

    void delete(Todo todo);

}
