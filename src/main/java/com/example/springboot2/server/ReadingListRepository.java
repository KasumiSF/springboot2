package com.example.springboot2.server;

import com.example.springboot2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListRepository extends JpaRepository<Book,Long> {

    List<Book> findByReader (String reader);
}
