package com.royhadad.bookstore.entities;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class CartBook {
    private long id;

    @Column(name = "bookId")
    private long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private int year;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    public CartBook() {

    }

    public CartBook(long bookId, String title, String author, int year, double price, int quantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBookId() {
        return this.bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return "id=" + this.id + ", bookId=" + this.bookId + ", title=" + this.title + ", author=" + this.author
                + ", year=" + this.year + ", price=" + this.price + ", quantity=" + this.quantity;
    }

    public static double getCartSum(List<CartBook> cart) {
        Iterator<CartBook> iterator = cart.iterator();
        CartBook currentCartBook;
        double sum = 0.0;
        while (iterator.hasNext()) {
            currentCartBook = iterator.next();
            sum += currentCartBook.getPrice() * currentCartBook.getQuantity();
        }
        return sum;
    }
}