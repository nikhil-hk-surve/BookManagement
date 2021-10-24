package com.dda.books.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookDto implements Serializable {

    public BookDto() {
    }

   private Book book;

    private String error;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "{" +
                "book=" + book +
                ", error='" + error + '\'' +
                '}';
    }
}
