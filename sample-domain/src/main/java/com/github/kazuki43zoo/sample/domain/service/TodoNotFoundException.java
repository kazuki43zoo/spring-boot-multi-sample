package com.github.kazuki43zoo.sample.domain.service;

public class TodoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TodoNotFoundException(String todoId) {
        super("Todo is not found (todoId = " + todoId + ")");
    }
}
