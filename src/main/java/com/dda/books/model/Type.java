package com.dda.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Type {

    public Type() {

    }

    public Type(Long id) {
        this.type_id = type_id;
    }

    public Type(Long type_id, String typeName, BigDecimal discount) {
        this.type_id = type_id;
        this.typeName = typeName;
        this.discount = discount;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_Generator")
    @SequenceGenerator(name = "type_Generator", sequenceName = "type_seq", allocationSize = 500)
    private Long type_id;

    private String typeName;

    private BigDecimal discount=new BigDecimal(0);

    @JsonIgnore
    @OneToMany(mappedBy = "type")
    private Set<Book> books=new HashSet<>();

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
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

    @Override
    public String toString() {
        return "Type{" +
                "type_id=" + type_id +
                ", typeName='" + typeName + '\'' +
                ", discount=" + discount +
                '}';
    }
}
