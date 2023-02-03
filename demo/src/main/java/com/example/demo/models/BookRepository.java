package com.example.demo.models;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
  Collection<Book> findByPublisher(String publisher);
}
