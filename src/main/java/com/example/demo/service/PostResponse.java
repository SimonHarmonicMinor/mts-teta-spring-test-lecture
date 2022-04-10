package com.example.demo.service;

import java.util.Objects;

public class PostResponse {
  private final Long id;
  private final String title;
  private final String content;

  public PostResponse(Long id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }

  public Long getId() {
    return id;
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
    PostResponse that = (PostResponse) o;
    return Objects.equals(id, that.id) && Objects.equals(title, that.title)
        && Objects.equals(content, that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, content);
  }
}
