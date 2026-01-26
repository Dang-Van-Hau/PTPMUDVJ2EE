package com.example.Bai2.Controller;

import com.example.Bai2.Service.BookService;
import com.example.Bai2.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping
    public List<Book> getAllBooks() { return bookService.getAllBooks(); }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) { return bookService.getBookById(id); }

    @PostMapping
    public String addBook(@RequestBody Book book) { bookService.addBook(book); return "Add book success!"; }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        return "Update book success!";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) { bookService.deleteBook(id); return "Delete book success!"; }
}
