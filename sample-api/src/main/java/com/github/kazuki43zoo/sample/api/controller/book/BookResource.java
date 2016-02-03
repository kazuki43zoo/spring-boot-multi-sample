package com.github.kazuki43zoo.sample.api.controller.book;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class BookResource implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookId;

    @NotNull
    private String name;

    private java.time.LocalDate publishedDate;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

}
