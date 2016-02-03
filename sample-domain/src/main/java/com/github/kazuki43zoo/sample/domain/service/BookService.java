package com.github.kazuki43zoo.sample.domain.service;


import com.github.kazuki43zoo.sample.domain.model.Book;
import com.github.kazuki43zoo.sample.domain.repository.BookCriteria;
import com.github.kazuki43zoo.sample.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Book find(String bookId) {
        return bookRepository.findOne(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public Book delete(String bookId) {
        return bookRepository.delete(bookId);
    }

    public List<Book> findAllByCriteria(BookCriteria criteria) {
        return bookRepository.findAllByCriteria(criteria);
    }

}
