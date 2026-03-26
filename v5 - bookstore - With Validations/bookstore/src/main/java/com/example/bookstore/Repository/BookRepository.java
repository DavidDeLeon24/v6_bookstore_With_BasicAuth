package com.example.bookstore.Repository;

import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleIgnoreCase(String title);

    List<Book> findByCategoryIgnoreCase(String category);

    Optional<Book> findByCategoryIgnoreCaseAndTitleIgnoreCase(String category, String title);

    List<Book> findByCategoryIgnoreCaseAndAuthorIgnoreCase(String category, String author);
}