package com.dda.books.service;

import com.dda.books.Application;
import com.dda.books.dao.TypeRepository;
import com.dda.books.model.Type;
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
public class TypeServiceImplTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private TypeService typeService;

    @MockBean
    private TypeRepository typeRepository;

    Type type=new Type(1l, "Fantasy",new BigDecimal(20));

    @Test
    public void getTypeTest()
    {
        Optional<Type> opt= Optional.ofNullable(type);
        Mockito.when(typeRepository.findById(Mockito.anyLong())).thenReturn(opt);
        Optional<Type> tp=typeService.getType(1l);
        Assert.assertTrue(tp.get().getTypeName().equals("Fantasy"));
    }

    @Test
    public void getAllTypeTest()
    {
        List<Type> list=new ArrayList<>();
        list.add(type);

        Mockito.when(typeRepository.findAll()).thenReturn(list);
        List<Type> type=typeService.getAllTypes();
        Assert.assertTrue(list.get(0).getTypeName().equals("Fantasy"));
    }

    @Test
    public void saveTest() throws SQLException {
        Mockito.when(typeRepository.save(Mockito.any(Type.class))).thenReturn(type);
        Type savedType=typeService.save(type);
        Assert.assertTrue(savedType.getTypeName().equals("Fantasy"));
    }

    @Test
    public void deleteTest1() throws SQLException {
        Mockito.doNothing().when(typeRepository).deleteById(Mockito.anyLong());
        typeService.delete(1l);
        Mockito.verify(typeRepository, Mockito.times(1)).deleteById(1l);
    }

    @Test
    public void deleteTest() throws SQLException {
        Mockito.doNothing().when(typeRepository).deleteById(Mockito.anyLong());
        typeRepository.deleteById(1l);
        Mockito.verify(typeRepository, Mockito.times(1)).deleteById(1l);
    }

    @Test
    public void deleteAllTest() throws Exception {

        Mockito.doNothing().when(typeRepository).deleteAll();

        typeRepository.deleteAll();

        Mockito.verify(typeRepository,Mockito.times(1)).deleteAll();

    }
}
