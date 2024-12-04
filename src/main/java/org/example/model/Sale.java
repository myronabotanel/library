package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sale {
    private Long id;
    private String bookTitle;
    private String sellerName;
    private int quantity;
    private Double totalPrice;
    private LocalDateTime orderDate;

    public Sale(Long id, String bookTitle, String sellerName, int quantity, Double totalPrice, LocalDateTime orderDate) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.sellerName = sellerName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }
    public Sale(){}

    public Long getId() {
        return id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getSellerName() {
        return sellerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                '}';
    }
}
