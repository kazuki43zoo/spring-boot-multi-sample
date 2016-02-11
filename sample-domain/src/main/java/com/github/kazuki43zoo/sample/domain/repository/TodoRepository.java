package com.github.kazuki43zoo.sample.domain.repository;

import com.github.kazuki43zoo.sample.domain.model.Todo;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.Collection;

public interface TodoRepository {

    @PostAuthorize("(returnObject == null) " +
            "or (returnObject.username == principal.username)")
    Todo findOne(String todoId);

    Collection<Todo> findAll();

    void create(Todo todo);

    boolean update(Todo todo);

    void delete(Todo todo);

    long countByFinished(boolean finished);
}
