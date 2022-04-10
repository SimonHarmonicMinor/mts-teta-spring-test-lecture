package com.example.demo.entity;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "post",
    uniqueConstraints = @UniqueConstraint(columnNames = "title")
)
public class Post {

  public static Post newPost(String title, String content) {
    Post post = new Post();
    post.setTitle(title);
    post.setContent(content);
    return post;
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
