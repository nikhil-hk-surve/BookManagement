package com.dda.books.controller;

import com.dda.books.model.Book;
import com.dda.books.model.BookDto;
import com.dda.books.model.CheckOutDto;
import com.dda.books.model.Type;
import com.dda.books.service.BookService;
import com.dda.books.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/rest/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    TypeService typeService;

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBook(@PathVariable Long id)
    {
        Optional<Book> optionalBook= bookService.getBook(id);
        return optionalBook.isPresent()?ResponseEntity.of(optionalBook):ResponseEntity.ok("No Book with this ID Available");
    }

    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllBooks()
    {
        return bookService.getAllBooks();

    }

    //@RequestMapping(value="/save", method = RequestMethod.POST)
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto save(@RequestBody Book book) {

        BookDto dto=new BookDto();
        try {
            if(book.getType()!=null) {
                Optional<Type> type = typeService.getType(book.getType().getType_id());
                if (!type.isPresent() && !StringUtils.isEmpty(book.getType().getTypeName())) {
                    Type tp=book.getType();
                    //typeService.save(tp);
                    book.setType(tp);
                    dto.setError("None");
                }else if(!type.isPresent())
                {
                    book.setType(null);
                    dto.setError("Record created with No Type");
                }else if(type.isPresent())
                {
                    book.setType(type.get());
                    dto.setError("None");
                }
            }

            bookService.save(book);
            Optional<Book> resp=bookService.getBook(book.getId());
            dto.setBook(resp.orElse(book));
        }catch (SQLException se)
        {
            dto.setError(se.getMessage());
        }catch(Exception ex)
        {
            dto.setError("Unknown Error: "+ex.getMessage());
        }

        return dto;
    }

    @PostMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String delete(@PathVariable Long id)
    {
        bookService.delete(id);

        return "Book Record "+id+" Deleted";
    }

    @PostMapping(value = "/deleteAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAll()
    {
        bookService.deleteAll();

        return "All Books are Deleted";
    }

    @PostMapping(value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public CheckOutDto getDiscount()
    {
        CheckOutDto chk=new CheckOutDto();
        List<Book> list=bookService.getAllBooks();
        BigDecimal discountedAmount=new BigDecimal(0);
        BigDecimal bkAmt=new BigDecimal(0);
        BigDecimal disc=new BigDecimal(0);
        BigDecimal total=new BigDecimal(0);

        for(Book bk:list)
        {
            bkAmt=bk.getPrice();
            disc=bk.getType().getDiscount();
            discountedAmount=discountedAmount.add(bkAmt.multiply(new BigDecimal(100).subtract(disc)).divide(new BigDecimal(100),2));
            total=total.add(bkAmt);
        }

        chk.setDiscountedPrice(discountedAmount);
        chk.setTotalAmount(total);
        chk.setTotalDiscount(total.subtract(discountedAmount).divide(total,2).multiply(new BigDecimal(100)));
        return chk;
    }

}
