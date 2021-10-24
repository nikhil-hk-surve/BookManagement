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
import java.math.RoundingMode;
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
            Type tp=book.getType();
            if(tp!=null && tp.getType_id()!=null) {
                Optional<Type> type = typeService.getType(tp.getType_id());
                if(!type.isPresent() && !StringUtils.isEmpty(tp.getTypeName()))
                {
                    tp.setType_id(null);
                    book.setType(tp);
                    dto.setError("Record created might have different Type_id. Use /book/{id} to check");
                }else if(!type.isPresent()){
                    book.setType(null);
                    dto.setError("Record created with No Type");
                }else
                {
                    book.setType(type.get());
                    dto.setError("None");
                }
            }else if (tp!=null && !StringUtils.isEmpty(tp.getTypeName())) {

                book.setType(tp);
                dto.setError("None");
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
    public void delete(@PathVariable Long id)
    {
        bookService.delete(id);

    }

    @PostMapping(value = "/deleteAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll()
    {
        bookService.deleteAll();

    }

    @PostMapping(value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public CheckOutDto checkout()
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
        chk.setTotalDiscount((total.subtract(discountedAmount)).divide(total,2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        return chk;
    }

}
