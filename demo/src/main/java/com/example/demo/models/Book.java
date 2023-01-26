package com.example.demo.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "isbn", nullable = false)
  private String isbn;

  @Column(name = "title", nullable = true)
  private String title;

  @Column(name = "price", nullable = false)
  private int price;

  @Column(name = "publisher", nullable = false)
  private String publisher;

  @Column(name = "published", nullable = false)
  private LocalDate published;

  @Column(name = "attach", nullable = true)
  public String attach;
}
