package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
