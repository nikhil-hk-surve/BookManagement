package com.dda.books.service;

import com.dda.books.model.Type;

import java.util.List;
import java.util.Optional;

public interface TypeService {
    public Optional<Type> getType(Long id);

    public List<Type> getAllTypes();

    public Type save(Type type);

    public void delete(Long id);

    public void deleteAll();
}
