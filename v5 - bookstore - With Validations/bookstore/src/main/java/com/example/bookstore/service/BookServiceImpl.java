package com.example.bookstore.service;

import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.dto.request.createBookRequest;
import com.example.bookstore.dto.response.BookResponse;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookResponse> getAllBooks(String category) {
        List<Book> books;

        if(category == null || category.isBlank()){
            books = bookRepository.findAll();
        }
        else{
            books = bookRepository.findByCategoryIgnoreCase(category);
        }

        return books.stream().map(bookMapper::toResponse)
                .toList();

    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found with id" + id));

        return bookMapper.toResponse(book);
    }

    @Override
    @Transactional
    public BookResponse createBook(createBookRequest request) {
        Book book = bookMapper.toEntity(request);
        Book savedBook = bookRepository.save(book);

        return bookMapper.toResponse(savedBook);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book book) {
        return bookRepository.findById(id)
                .map(existing -> {
                            existing.setTitle(book.getTitle());
                            existing.setAuthor(book.getAuthor());
                            existing.setCategory(book.getCategory());
                            return bookRepository.save(existing);
                        }
                ).orElseThrow(
                        () -> new ResourceNotFoundException("Book not found with id" + id));
    }

    @Override
    @Transactional
    public Book partialUpdate(Long id, Book partialBook) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    if (partialBook.getTitle() != null)
                        existingBook.setTitle(partialBook.getTitle());

                    if (partialBook.getAuthor() != null)
                        existingBook.setAuthor(partialBook.getAuthor());

                    if (partialBook.getCategory() != null)
                        existingBook.setCategory(partialBook.getCategory());

                    return bookRepository.save(existingBook);
                }).orElseThrow(
                () -> new ResourceNotFoundException("Book not found with id" + id));
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
    }
}
