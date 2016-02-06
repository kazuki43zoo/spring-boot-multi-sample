package com.github.kazuki43zoo.sample.domain.service;

public class BookNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookNotFoundException(String bookId) {
        super("Book is not found (bookId = " + bookId + ")");
    }
}
