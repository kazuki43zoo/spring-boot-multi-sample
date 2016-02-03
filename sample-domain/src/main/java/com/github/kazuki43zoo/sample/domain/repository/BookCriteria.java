package com.github.kazuki43zoo.sample.domain.repository;

import java.io.Serializable;
import java.time.LocalDate;

public class BookCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private LocalDate publishedDate;

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
