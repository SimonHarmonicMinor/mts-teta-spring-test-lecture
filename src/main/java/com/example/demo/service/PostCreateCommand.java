package com.example.demo.service;

public class PostCreateCommand {
  private final String title;
  private final String content;

  public PostCreateCommand(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }
}
