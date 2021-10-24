package com.dda.books.controller;

import com.dda.books.Application;
import com.dda.books.controller.BookController;
import com.dda.books.model.Book;
import com.dda.books.model.BookDto;
import com.dda.books.model.CheckOutDto;
import com.dda.books.model.Type;
import com.dda.books.service.BookService;
import com.dda.books.service.TypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@RunWith(SpringRunner.class)
@SpringBootTest(value= "false", classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private TypeService typeService;

    @Autowired
    private MockMvc mvc;

    Type type=new Type(1l, "Fantasy",new BigDecimal(20));
    Book book=new Book(1l,"Harry Potter","Children's book",
            "J.K.Rowling",type,new BigDecimal(100.00),"8273828282828");

    String result = "{\"id\": 1,\n" +
            "   \"name\": \"Harry Potter\",\n" +
            "   \"description\": \"Children's book\",\n" +
            "   \"author\": \"J.K.Rowling\",\n" +
            "   \"type\":{\n" +
            "     \"type_id\": 1,\n" +
            "     \"typeName\": \"Fantasy\",\n" +
            "     \"discount\": 20\n" +
            "   },\n" +
            "   \"price\": 100.00,\n" +
            "   \"isbn\": \"8273828282828\"}";

    String resultList = "[{\"id\": 1,\n" +
            "   \"name\": \"Harry Potter\",\n" +
            "   \"description\": \"Children's book\",\n" +
            "   \"author\": \"J.K.Rowling\",\n" +
            "   \"type\":{\n" +
            "     \"type_id\": 1,\n" +
            "     \"typeName\": \"Fantasy\",\n" +
            "     \"discount\": 20\n" +
            "   },\n" +
            "   \"price\": 100.00,\n" +
            "   \"isbn\": \"8273828282828\"}]";


    String dtoResp = "{\n" +
            "   \"book\":    {\n" +
            "      \"id\": 1,\n" +
            "      \"name\": \"Harry Potter\",\n" +
            "      \"description\": \"Children's book\",\n" +
            "      \"author\": \"J.K.Rowling\",\n" +
            "      \"type\":       {\n" +
            "         \"type_id\": 1,\n" +
            "         \"typeName\": \"Fantasy\",\n" +
            "         \"discount\": 20\n" +
            "      },\n" +
            "      \"price\": 100.0,\n" +
            "      \"isbn\": \"8273828282828\"\n" +
            "   },\n" +
            "   \"error\": \"None\"\n" +
            "}";

    String chkResp="{\n" +
            "   \"totalAmount\": 100,\n" +
            "   \"discountedPrice\": 80,\n" +
            "   \"totalDiscount\": 20\n" +
            "}";

    @Test
    public void getBookTest() throws Exception {
        Mockito.when(bookService.getBook(Mockito.anyLong())).thenReturn(Optional.ofNullable(book));

        RequestBuilder requestBuilder= MockMvcRequestBuilders.get("/rest/book/1").accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult=mvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(result,mvcResult.getResponse().getContentAsString(),false);
    }



    @Test
    public void getAllBookTest() throws Exception {

        List<Book> list=new ArrayList<>();
        list.add(book);

        Mockito.when(bookService.getAllBooks()).thenReturn(list);

        RequestBuilder requestBuilder= MockMvcRequestBuilders.get("/rest/book/all").accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult=mvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(resultList,mvcResult.getResponse().getContentAsString(),false);
    }

    @Test
    public void saveTest() throws Exception {
        BookDto dto=new BookDto();
        dto.setBook(book);
        Mockito.when(bookService.save(Mockito.any(Book.class))).thenReturn(book);

        Mockito.when(typeService.getType(Mockito.anyLong())).thenReturn(Optional.ofNullable(type));

        RequestBuilder requestBuilder= MockMvcRequestBuilders.post("/rest/book/save")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(result).contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult=mvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(dtoResp,mvcResult.getResponse().getContentAsString(),false);
    }


    @Test
    public void deleteAllTest() throws Exception {

        Mockito.doNothing().when(bookService).deleteAll();

        bookService.deleteAll();

        Mockito.verify(bookService,Mockito.times(1)).deleteAll();

    }

    @Test
    public void checkoutTest() throws Exception {
        CheckOutDto dto=new CheckOutDto();

        List<Book> list=new ArrayList<>();
        list.add(book);

        Mockito.when(bookService.getAllBooks()).thenReturn(list);

        RequestBuilder requestBuilder= MockMvcRequestBuilders.post("/rest/book/checkout")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult=mvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(chkResp,mvcResult.getResponse().getContentAsString(),false);
    }

}
