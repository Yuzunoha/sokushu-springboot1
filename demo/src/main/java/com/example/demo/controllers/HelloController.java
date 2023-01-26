package com.example.demo.controllers;

import com.example.demo.models.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

  private BookRepository rep;

  public HelloController(BookRepository rep) {
    this.rep = rep;
  }

  @GetMapping("/hello")
  @ResponseBody
  public String index() {
    return "こんにちは、世界！";
  }

  @GetMapping("/greet")
  public String greet(Model model) {
    model.addAttribute("message", "こんにちは、世界！");
    return "greet";
  }

  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("books", rep.findAll());
    return "list";
  }
}
