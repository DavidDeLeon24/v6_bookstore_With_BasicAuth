package com.example.bookstore.mapper;

import com.example.bookstore.dto.request.createBookRequest;
import com.example.bookstore.dto.response.BookResponse;
import com.example.bookstore.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(createBookRequest request){
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setCategory(request.getCategory());
        return book;
    }

    public BookResponse toResponse(Book book){
        return new BookResponse(book.getAuthor(), book.getCategory(), book.getId(), book.getTitle());
    }
}
