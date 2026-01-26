package com.example.Bai2.Service;

import com.example.Bai2.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public BookService() {
        books.add(new Book(1, "Clean Code", "Robert C. Martin"));
        books.add(new Book(2, "Effective Java", "Joshua Bloch"));
    }

    public List<Book> getAllBooks() { return books; }

    public Book getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public void addBook(Book book) { books.add(book); }

    public void updateBook(int id, Book updatedBook) {
        books.stream().filter(b -> b.getId() == id).findFirst()
                .ifPresent(b -> { b.setTitle(updatedBook.getTitle()); b.setAuthor(updatedBook.getAuthor()); });
    }

    public void deleteBook(int id) { books.removeIf(b -> b.getId() == id); }
}
