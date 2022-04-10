package com.example.demo.service;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostCreateCommand that = (PostCreateCommand) o;
    return Objects.equals(title, that.title) && Objects.equals(content,
        that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, content);
  }
}
