package com.dda.books.service;

import com.dda.books.Application;
import com.dda.books.dao.BookRepository;
import com.dda.books.model.Book;
import com.dda.books.model.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(value= "false", classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookServiceImplTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    Type type=new Type(1l, "Fantasy",new BigDecimal(20));
    Book book=new Book(1l,"Harry Potter","Children's book",
            "J.K.Rowling",type,new BigDecimal(100.00),"8273828282828");

    @Before
    public void setup()
    {


    }

    @Test
    public void getBookTest()
    {
        Optional<Book> opt= Optional.ofNullable(book);
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(opt);
        Optional<Book> book=bookService.getBook(1l);
        Assert.assertTrue(book.get().getName().equals("Harry Potter"));
    }

    @Test
    public void getAllBookTest()
    {
        List<Book> list=new ArrayList<>();
        list.add(book);

        Mockito.when(bookRepository.findAll()).thenReturn(list);
        List<Book> book=bookService.getAllBooks();
        Assert.assertTrue(list.get(0).getName().equals("Harry Potter"));
    }

    @Test
    public void saveTest() throws SQLException {
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        Book savedBook=bookService.save(book);
        Assert.assertTrue(savedBook.getName().equals("Harry Potter"));
    }

    @Test
    public void deleteTest1() throws SQLException {
        Mockito.doNothing().when(bookRepository).deleteById(Mockito.anyLong());
        bookService.delete(1l);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(1l);
    }

    @Test
    public void deleteTest() throws SQLException {
        Mockito.doNothing().when(bookRepository).deleteById(Mockito.anyLong());
        bookRepository.deleteById(1l);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(1l);
    }

    @Test
    public void deleteAllTest() throws Exception {

        Mockito.doNothing().when(bookRepository).deleteAll();

        bookRepository.deleteAll();

        Mockito.verify(bookRepository,Mockito.times(1)).deleteAll();

    }

}
