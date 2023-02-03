package com.example.demo.controllers;

import com.example.demo.models.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/record")
@Controller
public class RecordController {

  private final BookRepository rep;

  protected static final String COMMON_LAYOUT = "common/layout";

  @GetMapping("/find/{id}")
  public String find(@PathVariable int id, Model model) {
    // 検索結果を入れている
    model.addAttribute("book", rep.findById(id));
    model.addAttribute("main", "record/find::main");
    return COMMON_LAYOUT;
  }
}
