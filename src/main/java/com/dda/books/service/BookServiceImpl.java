package com.dda.books.service;

import com.dda.books.dao.BookRepository;
import com.dda.books.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository repository;

    @Override
    public Optional<Book> getBook(Long id) {


        return repository.findById(id);
    }

    @Override
    public Book save(Book book) throws SQLException {
        try {
            repository.save(book);
            repository.flush();
        }catch (Exception ex){
            if(ex instanceof DataIntegrityViolationException)
            {
                throw new SQLException("Type_id does not Exist. Please check the Type first.");
            }
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {

        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Book> findAllById(List<Long> ids) {
        return repository.findAllById(ids);
    }


}
