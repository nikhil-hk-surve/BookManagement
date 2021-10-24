package com.dda.books.dao;

import com.dda.books.Application;
import com.dda.books.model.Book;
import com.dda.books.model.Type;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(value= "false", classes = Application.class)
@ExtendWith(MockitoExtension.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    Type type=new Type(1l, "Fantasy",new BigDecimal(20));
    Book book=new Book(1l,"Harry Potter","Children's book",
            "J.K.Rowling",type,new BigDecimal(100.00),"8273828282828");

    @Before
    public void setup()
    {
        book.setType(type);
        List<Book> list= new ArrayList<>();
        list.add(book);
        bookRepository.saveAll(list);

    }

    @Test
    public void testBGetBooks()
    {

        List<Book> list=bookRepository.findAll();
        Assert.assertTrue(list.size()>=1);
    }


    @Test
    public void testAGetBook()
    {
        Optional<Book> opt=bookRepository.findById(1l);
        Assert.assertTrue(opt.isPresent());
    }

    @Test
    public void testCSaveBooks()
    {
        Type type=new Type(1l, "Fantasy",new BigDecimal(20));
        Book book=new Book(2l,"Harry Potter","Children's book",
                "J.K.Rowling",type,new BigDecimal(100.00),"8273828282828");
        book.setType(type);
        bookRepository.save(book);
        Assert.assertTrue(bookRepository.findById(2l).isPresent());
    }

    @Test
    public void testDeleteBooks()
    {
        bookRepository.deleteAll();
        Assert.assertTrue(!bookRepository.findById(1l).isPresent());
    }

}
