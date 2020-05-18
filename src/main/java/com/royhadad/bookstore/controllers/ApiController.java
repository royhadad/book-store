package com.royhadad.bookstore.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.royhadad.bookstore.Book;
import com.royhadad.bookstore.BookRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return this.bookRepo.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable(value = "id") long id) {
        System.out.println("hi getting" + id);
        Optional<Book> book = this.bookRepo.findById(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/books")
    public Book createBook(@Valid @RequestBody Book book) {
        System.out.println("POST @/api/books, book: " + book.toString());
        return bookRepo.save(book);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable(value = "id") Long bookId) {
        Book book = bookRepo.findById(bookId).orElseGet(() -> null);
        if (book == null) {
            return ResponseEntity.status(404).body(Boolean.FALSE);
        } else {
            bookRepo.delete(book);
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Long bookId,
            @Valid @RequestBody Book bookDetails) {
        Book book = bookRepo.findById(bookId).orElseGet(() -> null);
        if (book == null) {
            return ResponseEntity.status(404).body(book);
        } else {
            book.setAuthor(bookDetails.getAuthor());
            book.setTitle(bookDetails.getTitle());
            final Book updatedBook = bookRepo.save(book);
            return ResponseEntity.ok().body(updatedBook);
        }
    }

}