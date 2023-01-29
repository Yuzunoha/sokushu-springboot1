package com.example.demo.models;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class MemberForm {

  private String name;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;

  private String email;
}
