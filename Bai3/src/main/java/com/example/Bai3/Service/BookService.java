package com.example.Bai3.Service;

import com.example.Bai3.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    public BookService() {
        books.add(new Book(1L, "Spring boot", "Huy Cuong"));
        books.add(new Book(2L, "Spring Boot V2", "Anh"));
        nextId = 3L;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void addBook(Book book) {
        book.setId(nextId++);
        books.add(book);
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    public void updateBook(Book updatedBook) {
        getBookById(updatedBook.getId()).ifPresent(existing -> {
            existing.setTitle(updatedBook.getTitle());
            existing.setAuthor(updatedBook.getAuthor());
        });
    }

    public void deleteBook(Long id) {
        books.removeIf(b -> b.getId().equals(id));
    }
}
