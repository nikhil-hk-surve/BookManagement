package com.dda.books.controller;


import com.dda.books.Application;
import com.dda.books.model.Book;
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

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@RunWith(SpringRunner.class)
@SpringBootTest(value= "false", classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TypeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TypeService typeService;

    Type type=new Type(1l, "Fantasy",new BigDecimal(0));

    String result = "{\n" +
            "   \"type_id\": 1,\n" +
            "   \"typeName\": \"Fantasy\",\n" +
            "   \"discount\": 0\n" +
            "}";

    String resultBooks = "{\n" +
            "   \"type_id\": 1,\n" +
            "   \"typeName\": \"Fantasy\",\n" +
            "   \"discount\": 0,\n" +
            "   \"books\": []\n" +
            "}";

    String resultList = "[{\n" +
            "   \"type_id\": 1,\n" +
            "   \"typeName\": \"Fantasy\",\n" +
            "   \"discount\": 0\n" +
            "}]";


    @Test
    public void getTypeTest() throws Exception {
        Mockito.when(typeService.getType(Mockito.anyLong())).thenReturn(Optional.ofNullable(type));
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get("/rest/type/1").accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult=mvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(result,mvcResult.getResponse().getContentAsString(),false);
    }

    @Test
    public void getAllTypeTest() throws Exception {
        List<Type> list=new ArrayList<>();
        list.add(type);

        Mockito.when(typeService.getAllTypes()).thenReturn(list);
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get("/rest/type/all").accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult=mvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(resultList,mvcResult.getResponse().getContentAsString(),false);
    }

    @Test
    public void saveTest() throws Exception {

        Mockito.when(typeService.save(Mockito.any(Type.class))).thenReturn(type);

        RequestBuilder requestBuilder= MockMvcRequestBuilders.post("/rest/type/save")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(result).contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult=mvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(result,mvcResult.getResponse().getContentAsString(),false);
    }

    @Test
    public void deleteTest() throws Exception {

        Mockito.doNothing().when(typeService).delete(Mockito.anyLong());

        typeService.delete(1l);

        Mockito.verify(typeService,Mockito.times(1)).delete(1l);

    }

    @Test
    public void deleteAllTest() throws Exception {

        Mockito.doNothing().when(typeService).deleteAll();

        typeService.deleteAll();

        Mockito.verify(typeService,Mockito.times(1)).deleteAll();

    }


}
