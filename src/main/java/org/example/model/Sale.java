package org.example.model;

import java.time.LocalDate;

public class Sale {
    private Book book;
    private LocalDate saleDate;
    private double price;

    public Sale(Book book, LocalDate saleDate, double price) {
        this.book = book;
        this.saleDate = saleDate;
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "book=" + book.getTitle() +
                ", saleDate=" + saleDate +
                ", price=" + price +
                '}';
    }
}
