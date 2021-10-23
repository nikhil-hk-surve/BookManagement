package com.dda.books.dao;

import java.util.List;
import com.dda.books.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    @Override
    @Query("select t from Type t")
    List<Type> findAll();


}
