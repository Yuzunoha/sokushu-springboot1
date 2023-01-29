package com.example.demo.controllers;

import com.example.demo.models.MemberForm;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.function.UnaryOperator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

  private String getStrCommonLayout() {
    return "common/layout";
  }

  @PostMapping("/form")
  public String formResult(@ModelAttribute MemberForm memberForm, Model model) {
    model.addAttribute("main", "ctrl/form_result::main");
    return getStrCommonLayout();
  }

  @GetMapping("/upload")
  public String upload(Model model) {
    model.addAttribute("main", "ctrl/upload::main");
    return getStrCommonLayout();
  }

  @PostMapping("/upload")
  public String upload(
    Model model,
    @RequestParam("upfile") MultipartFile file
  ) {
    String name = file.getOriginalFilename();
    UnaryOperator<String> getMsg = (String msg) ->
      name + "のアップロードに" + msg + "しました";

    try (
      var bof = new BufferedOutputStream(new FileOutputStream("./" + name))
    ) {
      bof.write(file.getBytes());
      model.addAttribute("success", getMsg.apply("成功"));
    } catch (IOException e) {
      model.addAttribute("success", getMsg.apply("失敗"));
      e.printStackTrace();
    }

    model.addAttribute("main", "ctrl/upload::main");
    return getStrCommonLayout();
  }
}
