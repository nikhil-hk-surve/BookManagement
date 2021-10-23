package com.dda.books.service;

import com.dda.books.model.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    public Optional<Book> getBook(Long id);

    public Book save(Book book) throws SQLException;

    public List<Book> getAllBooks();

    public void delete(Long id);

    public void deleteAll();

    //public void update(Book book);
}
