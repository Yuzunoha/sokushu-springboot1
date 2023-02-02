package com.example.demo.views;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

public class PdfBasicView extends AbstractView {

  @Override
  public void renderMergedOutputModel(
    Map<String, Object> model,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
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
      // 文字列を出力する(ここがコントローラのときと変わる)
      doc.add(new Paragraph((String) model.get("msg")));
      doc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
