package com.royhadad.bookstore.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.royhadad.bookstore.entities.Book;
import com.royhadad.bookstore.entities.CartBook;
import com.royhadad.bookstore.repos.BookRepo;
import com.royhadad.bookstore.repos.CartRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private CartRepo cartRepo;

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

    @PostMapping(value = "/books", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Book createBook(@Valid Book book) {
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

    @PostMapping("/shopping-cart")
    public ResponseEntity<Object> addBookToCart(@RequestBody String bookId) {
        Long id = Long.parseLong(bookId);
        Book book = bookRepo.findById(id).orElseGet(() -> null);
        if (book == null) {
            return ResponseEntity.status(400).build();
        }

        CartBook cartBook = new CartBook();
        cartBook.setBookId(book.getId());
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "title", "author", "year", "price",
                "quantity");

        cartBook = cartRepo.findOne(Example.of(cartBook, matcher)).orElseGet(() -> null);
        if (cartBook == null) {
            cartBook = new CartBook(book.getId(), book.getTitle(), book.getAuthor(), book.getYear(), book.getPrice(),
                    1);
        } else {
            cartBook.setQuantity(cartBook.getQuantity() + 1);
        }
        cartRepo.save(cartBook);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/shopping-cart")
    public ResponseEntity<Object> deleteCart() {
        cartRepo.deleteAll();
        return ResponseEntity.ok().build();
    }
}