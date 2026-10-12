package com.example.library.model;

public class Book {
    private String bookId;
    private String title;
    private int stock;

    public Book(String bookId, String title, int stock) {
        this.bookId = bookId;
        this.title = title;
        this.stock = stock;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public int getStock() {
        return stock;
    }

    public boolean decreaseStock() {
        if (getStock() > 0) {
            this.stock--;
            return true;
        }

        return false;
    }

    public int increaseStock() {
        return this.stock++;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", stock=" + stock +
                '}';
    }
}