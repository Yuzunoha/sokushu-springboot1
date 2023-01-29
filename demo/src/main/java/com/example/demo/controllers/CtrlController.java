package com.example.demo.controllers;

import com.example.demo.models.MemberForm;
import java.util.Optional;
import java.util.function.UnaryOperator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    UnaryOperator<String> f = (String s) -> "id(ラムダ): " + s;
    return f.apply(id);
  }

  @GetMapping({ "/paramAny/{id}", "/paramAny" })
  @ResponseBody
  public String paramAny(@PathVariable(required = false) Optional<String> id) {
    return "id: " + id.orElse("パスパラメタ id は指定されませんでした。");
  }

  @GetMapping("/form")
  public String form(@ModelAttribute MemberForm memberForm, Model model) {
    return "ctrl/form";
  }

  @PostMapping("/form")
  public String formResult(@ModelAttribute MemberForm memberForm, Model model) {
    var valueMain = "ctrl/form_result::main";
    // モデルに詰め替え
    model.addAttribute("main", valueMain);
    return "common/layout";
  }
}
