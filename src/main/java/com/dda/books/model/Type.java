package com.dda.books.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Type {

    public Type() {

    }

    public Type(Long id) {
        this.id = id;
    }

    public Type(Long id, String typeName, BigDecimal discount) {
        this.id = id;
        this.typeName = typeName;
        this.discount = discount;

    }

    @Id
    @GeneratedValue
    @JsonProperty("type_id")
    private Long id;

    private String typeName;

    private BigDecimal discount;

    @OneToMany(mappedBy = "type")
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
