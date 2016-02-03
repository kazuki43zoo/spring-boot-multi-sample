package com.github.kazuki43zoo.sample.domain.repository;

import com.github.kazuki43zoo.sample.domain.model.Book;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class BookRepository {

    private final Map<String, Book> repository = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadDummyData() {
        Book book = new Book();
        book.setBookId("00000000-0000-0000-0000-000000000000");
        book.setName("書籍名");
        book.setPublishedDate(LocalDate.of(2010, 4, 20));
        repository.put(book.getBookId(), book);
    }

    public Optional<Book> findOne(String bookId) {
        Book book = repository.get(bookId);
        return Optional.ofNullable(book);
    }

    public Book save(Book book) {
        if (book.getBookId() == null || !repository.containsKey(book.getBookId())) {
            String bookId = UUID.randomUUID().toString();
            book.setBookId(bookId);
        }
        repository.put(book.getBookId(), book);
        return book;
    }

    public Book delete(String bookId) {
        return repository.remove(bookId);
    }


    public List<Book> findAllByCriteria(BookCriteria criteria) {
        return repository.values().stream()
                .filter(book ->
                        (criteria.getName() == null
                                || book.getName().contains(criteria.getName()))
                                &&
                                (criteria.getPublishedDate() == null
                                        || book.getPublishedDate().equals(criteria.getPublishedDate()))
                )
                .sorted((o1, o2) -> o1.getPublishedDate().compareTo(o2.getPublishedDate()))
                .collect(Collectors.toList());
    }

}
