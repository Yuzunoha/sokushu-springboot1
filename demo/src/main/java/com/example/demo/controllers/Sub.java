package com.example.demo.controllers;

public class Sub {

  public void run() {
    p(onlyReturn("こんにちは！"));
    p(onlyReturn(123));
    p(onlyReturn(new Cat(3)));
  }

  public static <T> void p(T a) {
    System.out.println(a);
  }

  public static <T> T onlyReturn(T a) {
    return a;
  }
}

abstract class Animal {

  protected int age;

  protected Animal(int age) {
    this.age = age;
  }

  abstract String cry();
}

class Cat extends Animal {

  public Cat(int age) {
    super(age);
  }

  @Override
  public String cry() {
    return "にゃー(" + this.age + ")";
  }

  @Override
  public String toString() {
    return this.cry();
  }
}
