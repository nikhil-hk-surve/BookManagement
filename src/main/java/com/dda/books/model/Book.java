package com.dda.books.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class Book {

    public Book() {
    }

    public Book(Long id, String name, String description, String author, Type type, BigDecimal price, String isbn) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.type = type;
        this.price = price;
        this.isbn = isbn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_Generator")
    @SequenceGenerator(name = "book_Generator", sequenceName = "book_seq", allocationSize = 1000)
    private Long id;

    private String name;

    private String description;

    private String author;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private Type type;

    private BigDecimal price=new BigDecimal(0);

    private String isbn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
