package com.dda.books.controller;

import com.dda.books.model.Book;
import com.dda.books.model.BookDto;
import com.dda.books.model.CheckOutDto;
import com.dda.books.service.BookService;
import com.dda.books.service.TypeService;
import com.dda.books.util.BookDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public List<BookDto> getAllBooks()
    {
        BookDtoMapper mapper=new BookDtoMapper();
        List<BookDto> list=new ArrayList<>();
         List<Book> books=bookService.getAllBooks();
         for(Book bk:books)
         {
             BookDto dto= mapper.mapBookDto(bk);
             list.add(dto);
         }

         return list;

    }

    //@RequestMapping(value="/save", method = RequestMethod.POST)
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto save(@RequestBody Book book) {
        BookDtoMapper mapper=new BookDtoMapper();
        BookDto dto=new BookDto();
        try {
            bookService.save(book);
            Optional<Book> resp=bookService.getBook(book.getId());
            dto=mapper.mapBookDto(resp.get());
        }catch (SQLException se)
        {
            dto.setError(se.getMessage());
        }

        return dto;
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto update(@RequestBody Book book)
    {
        return save(book);
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
