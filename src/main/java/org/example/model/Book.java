package org.example.model;

import java.time.LocalDate;

public class Book
{
    private Long id; //long ca sa fie obj, daca nu, va fi null by default, nu 0
    private String title;
    private String author;
    private LocalDate publishedDate;
    private Double price;
    private Integer stock;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Double getPrice() {return price;}

    public Integer getStock() {return stock;}

    public void setPrice(Double price) {this.price = price;}

    public void setStock(Integer stock) {this.stock = stock;}

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedDate=" + publishedDate +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}