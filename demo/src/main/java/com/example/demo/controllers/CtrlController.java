package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.MemberForm;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

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
    Consumer<String> setMsg = (String result) -> {
      var msg = name + "のアップロードに" + result + "しました";
      model.addAttribute("success", msg);
    };
    String result = "成功";

    try (
      var bof = new BufferedOutputStream(new FileOutputStream("./" + name))
    ) {
      bof.write(file.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
      result = "失敗";
    }

    setMsg.accept(result);
    model.addAttribute("main", "ctrl/upload::main");
    return getStrCommonLayout();
  }

  @GetMapping("/reqheader")
  @ResponseBody
  public String reqheader(@RequestHeader String accept) {
    return accept;
  }

  @GetMapping("/json")
  public String getJson() {
    return "ctrl/json";
  }

  @PostMapping("/json")
  @ResponseBody
  public String json(@RequestBody Book book) {
    String s = "";
    String d = " ... ";
    s += book.getId() + d;
    s += book.getIsbn() + d;
    s += book.getTitle() + d;
    s += book.getPrice() + d;
    s += book.getPublisher() + d;
    s += book.getPublished() + d;
    s += book.getAttach() + d;
    return s;
  }

  @GetMapping("/redirect")
  public String redirect() {
    // リダイレクトのビルドを試す
    var uri = MvcUriComponentsBuilder
      .fromMethodName(CtrlController.class, "param", "108")
      .build()
      .toUriString();
    return "redirect:" + uri;
  }

  @GetMapping("/forward1")
  public String forward1(HttpServletRequest request) {
    request.setAttribute("name", "山田");
    return "forward:/ctrl/forward2";
  }

  @GetMapping("/forward2")
  @ResponseBody
  public String forward2(HttpServletRequest request) {
    var name = request.getAttribute("name");
    return "こんにちは。" + name + "さん！";
  }

  @GetMapping("/response")
  public void response(HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_CREATED);
    response.setContentType("application/xml; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    try {
      response.getWriter().println("<result>成功</result>");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @GetMapping("/output.pdf")
  public void output(HttpServletResponse response) {
    response.setContentType("application/pdf");
    try {
      // ドキュメントを生成する
      ServletOutputStream outputStream = response.getOutputStream();
      PdfWriter pdfWriter = new PdfWriter(outputStream);
      PdfDocument pdf = new PdfDocument(pdfWriter);
      Document doc = new Document(pdf);
      // フォントを生成してドキュメントに設定する
      PdfFont font = PdfFontFactory.createFont(
        "HeiseiKakuGo-W5",
        "UniJIS-UCS2-H"
      );
      doc.setFont(font);
      // 文字列を出力する
      doc.add(new Paragraph("こんにちは、世界！"));
      doc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
