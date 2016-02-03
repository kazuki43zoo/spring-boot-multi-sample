package com.github.kazuki43zoo.sample.api.controller.book;


import com.github.kazuki43zoo.sample.domain.model.Book;
import com.github.kazuki43zoo.sample.domain.repository.BookCriteria;
import com.github.kazuki43zoo.sample.domain.service.BookNotFoundException;
import com.github.kazuki43zoo.sample.domain.service.BookService;
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
@RequestMapping("books")
public class BooksRestController {

    @Autowired
    BookService bookService;

    @RequestMapping(path = "{bookId}", method = RequestMethod.GET)
    public BookResource getBook(@PathVariable String bookId) {

        Book book = bookService.find(bookId);

        BookResource resource = new BookResource();
        resource.setBookId(book.getBookId());
        resource.setName(book.getName());
        resource.setPublishedDate(book.getPublishedDate());

        return resource;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createBook(
            @Validated @RequestBody BookResource newResource,
            UriComponentsBuilder uriBuilder) {

        Book newBook = new Book();
        newBook.setName(newResource.getName());
        newBook.setPublishedDate(newResource.getPublishedDate());

        Book createdBook = bookService.create(newBook);

        URI resourceUri = relativeTo(uriBuilder)
                .withMethodCall(
                        on(BooksRestController.class).getBook(createdBook.getBookId()))
                .build().encode().toUri();

        return ResponseEntity
                .created(resourceUri).build();
    }

    @RequestMapping(path = "{bookId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@PathVariable String bookId,
                    @Validated @RequestBody BookResource resource) {

        Book book = new Book();
        book.setBookId(bookId);
        book.setName(resource.getName());
        book.setPublishedDate(resource.getPublishedDate());

        bookService.update(book);

    }

    @RequestMapping(path = "{bookId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String bookId) {
        bookService.delete(bookId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BookResource> searchBooks(@Validated BookResourceQuery query) {

        BookCriteria criteria = new BookCriteria();
        criteria.setName(query.getName());
        criteria.setPublishedDate(query.getPublishedDate());

        List<BookResource> resources = new ArrayList<>();
        bookService.findAllByCriteria(criteria).forEach(book -> {
            BookResource resource = new BookResource();
            resource.setBookId(book.getBookId());
            resource.setName(book.getName());
            resource.setPublishedDate(book.getPublishedDate());
            resources.add(resource);
        });

        return resources;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooks() {
        bookService.findAllByCriteria(new BookCriteria())
                .stream().filter(book -> !book.getBookId().equals("00000000-0000-0000-0000-000000000000"))
                .forEach(book -> bookService.delete(book.getBookId()));
    }

}
