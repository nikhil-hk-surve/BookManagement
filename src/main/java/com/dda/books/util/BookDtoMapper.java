package com.dda.books.util;

import com.dda.books.model.Book;
import com.dda.books.model.BookDto;

public class BookDtoMapper {

    public BookDto mapBookDto(Book book)
    {
        BookDto dto=new BookDto();
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setType(book.getType().getTypeName());
        dto.setName(book.getName());
        dto.setPrice(book.getPrice());
        return dto;
    }
}
