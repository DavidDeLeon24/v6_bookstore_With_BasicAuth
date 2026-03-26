package com.example.bookstore.service;

import com.example.bookstore.dto.request.createBookRequest;
import com.example.bookstore.dto.response.BookResponse;
import com.example.bookstore.model.Book;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllBooks(String category);
    BookResponse getBookById(Long id);
    BookResponse createBook(createBookRequest bookRequest);
    Book updateBook(Long id, Book book);
    Book partialUpdate(Long id, Book partialBook);
    void deleteBook(Long id);
}
