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
        List<Book> list=bookService.getAllBooks();
        return calculateCheckoutItems(list, null);
    }

    @PostMapping(value = "/checkoutList/promotion/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CheckOutDto checkoutlist(@RequestBody List<Book> list, @PathVariable String code)
    {
        return calculateCheckoutItems(list, code);
    }

    private CheckOutDto calculateCheckoutItems(List<Book> list, String code)
    {
        CheckOutDto dto=new CheckOutDto();
        BigDecimal discountedAmount=new BigDecimal(0);
        BigDecimal bkAmt=new BigDecimal(0);
        BigDecimal disc=new BigDecimal(0);
        BigDecimal total=new BigDecimal(0);
        String type=null;
        BigDecimal promoDisc = new BigDecimal(0);

        List<Long> ids=new ArrayList<>();

        try {
        list.forEach(book->{
            ids.add(book.getId());
        });

        if(!StringUtils.isEmpty(code)) {
            List<Book> bkList=bookService.findAllById(ids);
            if(bkList.size()!=list.size())
            {
                throw new Exception("Few books are not in the system");
            }
            String[] promo = code.split("-");
            if(promo.length>1) {
                type = promo[0];
                promoDisc = new BigDecimal(promo[1]);
            }
        }


            if (list.size() >= 1) {
                for (Book bk : list) {
                    bkAmt = bk.getPrice();
                    if (bk.getType() == null || (bk.getType() != null && (StringUtils.isEmpty(bk.getType().getTypeName())
                            || bk.getType().getDiscount().equals(0)))) {
                        Optional<Book> book = bookService.getBook(bk.getId());
                        bk.setType(book.get().getType());
                    }
                    disc = bk.getType().getDiscount();

                    if (type != null && type.equalsIgnoreCase(bk.getType().getTypeName())) {
                        discountedAmount = discountedAmount.add(bkAmt.multiply(new BigDecimal(100).subtract(promoDisc)).divide(new BigDecimal(100), 2));

                    } else {
                        discountedAmount = discountedAmount.add(bkAmt.multiply(new BigDecimal(100).subtract(disc)).divide(new BigDecimal(100), 2));

                    }
                    total = total.add(bkAmt);
                }

                dto.setDiscountedPrice(discountedAmount);
                dto.setTotalAmount(total);
                dto.setTotalDiscount((total.subtract(discountedAmount)).divide(total, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
            }
        }catch(Exception ex)
        {
            dto.setError("Exception Occurred. Please check if any Book/Type is Valid ");
        }

            return dto;
    }

}
