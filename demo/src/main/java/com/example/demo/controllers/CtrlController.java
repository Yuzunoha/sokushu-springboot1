package com.example.demo.controllers;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/ctrl")
@Controller
public class CtrlController {

  @GetMapping("/index")
  @ResponseBody
  public String index() {
    return "/ctrl/index get です。";
  }

  @GetMapping("/param/{id}")
  @ResponseBody
  public String param(@PathVariable String id) {
    return "id: " + id;
  }

  @GetMapping({ "/paramAny/{id}", "/paramAny" })
  @ResponseBody
  public String paramAny(@PathVariable(required = false) Optional<String> id) {
    return "id: " + id.orElse("パスパラメタ id は指定されませんでした。");
  }
}
