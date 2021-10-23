package com.dda.books.service;

import com.dda.books.dao.TypeRepository;
import com.dda.books.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    TypeRepository typeRepository;

    @Override
    public Optional<Type> getType(Long id) {
        return typeRepository.findById(id);
    }

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public void save(Type type) {

        typeRepository.save(type);
    }

    @Override
    public void delete(Long id) {
        typeRepository.deleteById(id);

    }

    @Override
    public void deleteAll() {
        typeRepository.deleteAll();
    }
}
