package com.dda.books.dao;

import com.dda.books.model.Book;
import com.dda.books.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @Query("select b from Book b left join fetch b.type bt")
    List<Book> findAll();
}
