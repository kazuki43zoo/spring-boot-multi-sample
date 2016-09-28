package com.github.kazuki43zoo.sample.domain.repository;

import com.github.kazuki43zoo.sample.domain.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.List;

@Profile("api-client")
@Repository
@Primary
public class TodoApiRepository implements TodoRepository {

    @Value("${api.baseUrl:http://localhost:9081/api-r}/todos")
    String todosResourceUrl;

    @Value("${api.baseUrl:http://localhost:9081/api-r}/todos/{todoId}")
    String todoResourceUrl;

    private final RestOperations restOperations;

    public TodoApiRepository (RestTemplateBuilder builder) {
        this.restOperations = builder.build();
    }

    @PostAuthorize("permitAll()") // Override access policy on interface method
    @Override
    public Todo findOne(String todoId) {
        Todo todo = null;
        try {
            todo = restOperations.getForObject(todoResourceUrl, Todo.class, todoId);
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);
        }
        return todo;
    }

    @Override
    public List<Todo> findAllByUsername(String usename) {
        ResponseEntity<List<Todo>> responseEntity =
                restOperations.exchange(RequestEntity.get(URI.create(todosResourceUrl)).build(), new ParameterizedTypeReference<List<Todo>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public void create(Todo todo) {
        restOperations.postForLocation(todosResourceUrl, todo);
    }

    @Override
    public boolean update(Todo todo) {
        try {
            restOperations.put(todoResourceUrl, todo, todo.getTodoId());
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);
        }
        return true;
    }

    @Override
    public void delete(Todo todo) {
        try {
            restOperations.delete(todoResourceUrl, todo.getTodoId());
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);
        }
    }

    private void handleHttpClientErrorException(HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            return;
        }
        if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
            throw new AccessDeniedException(e.getResponseBodyAsString(), e);
        }
        throw e;
    }

}
