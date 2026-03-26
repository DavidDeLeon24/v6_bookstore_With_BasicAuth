package com.example.bookstore.controller;

import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.api.ApiResponse;
import com.example.bookstore.dto.request.createBookRequest;
import com.example.bookstore.dto.response.BookResponse;
import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books or filter by Category
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooks(@RequestParam(required = false) String category) {
        List<BookResponse> books = bookService.getAllBooks(category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Books fetched successfully", books)) ;
    }

    // Get book by Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable Long id) {
        BookResponse book = bookService.getBookById(id);
        return ResponseEntity.ok(new ApiResponse<>(true,  "Book fetched successfully", book));
    }

    // Create new book
    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid @RequestBody createBookRequest newBook) {
        BookResponse created = bookService.createBook(newBook);
        URI location = URI.create("/api/books/" + created.getId());

        return ResponseEntity.created(location).body(new ApiResponse<>(true, "Book created successfully", created));
    }

    // Update book by ID
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Long id,
                           @RequestBody Book updatedBook) {

        Book updated = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(new ApiResponse<>(true, "Book updated successfully", updated));
    }

    // Delete book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> partialUpdate(@PathVariable Long id,
                              @RequestBody Book partialBook) {
        Book updated = bookService.partialUpdate(id, partialBook);
        return ResponseEntity.ok(new ApiResponse<>(true, "Book partially updated", updated));
    }


}
