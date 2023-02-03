package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ApiController {

  private final BookRepository rep;

  @GetMapping("/list")
  public List<Book> list() {
    return rep.findAll();
  }
}
