package com.dda.books.dao;

import com.dda.books.dao.TypeRepository;
import com.dda.books.Application;
import com.dda.books.model.Book;
import com.dda.books.model.Type;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(value= "false", classes = Application.class)
@ExtendWith(MockitoExtension.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TypeRepositoryTest {

    @Autowired
    private TypeRepository typeRepository;

    Type type=new Type(1l, "Fantasy",new BigDecimal(20));
    Book book=new Book(1l,"Harry Potter","Children's type",
            "J.K.Rowling",type,new BigDecimal(100.00),"8273828282828");

    @Before
    public void setup()
    {
        book.setType(type);
        List<Type> list= new ArrayList<>();
        list.add(type);
        typeRepository.saveAll(list);

    }

    @Test
    public void testBGetTypes()
    {

        List<Type> list=typeRepository.findAll();
        Assert.assertTrue(list.size()>=1);
    }


    @Test
    public void testAGetType()
    {
        Optional<Type> opt=typeRepository.findById(1l);
        Assert.assertTrue(opt.isPresent());
    }

    @Test
    public void testCSaveTypes()
    {
        Type type=new Type(2l, "Fantasy",new BigDecimal(20));

        typeRepository.save(type);
        Assert.assertTrue(typeRepository.findById(2l).isPresent());
    }

    @Test
    public void testDeleteTypes()
    {
        typeRepository.deleteAll();
        Assert.assertTrue(!typeRepository.findById(1l).isPresent());
    }

}
