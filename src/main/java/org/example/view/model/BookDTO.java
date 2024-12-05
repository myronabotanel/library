package org.example.view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;

public class BookDTO {
    private StringProperty author;

    public void setAuthor(String author) {
        authorProperty().set(author);
    }

    public String getAuthor() {
        return authorProperty().get();
    }

    public StringProperty authorProperty() {
        if (author == null) {
            author = new SimpleStringProperty(this, "author");
        }
        return author;
    }

    private StringProperty title;

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public StringProperty titleProperty() {
        if (title == null) {
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }

    // Proprietăți pentru Price
    private DoubleProperty price;

    public void setPrice(double price) {
        priceProperty().set(price);
    }

    public double getPrice() {
        return priceProperty().get();
    }

    public DoubleProperty priceProperty() {
        if (price == null) {
            price = new SimpleDoubleProperty(this, "price");
        }
        return price;
    }

    // Proprietăți pentru Stock
        private IntegerProperty stock;

    public void setStock(int stock) {
        stockProperty().set(stock);
    }

    public int getStock() {
        return stockProperty().get();
    }

    public IntegerProperty stockProperty() {
        if (stock == null) {
            stock = new SimpleIntegerProperty(this, "stock");
        }
        return stock;
    }
    //pt date?
}
