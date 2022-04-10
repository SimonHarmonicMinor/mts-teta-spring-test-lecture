package com.example.demo.service;

import com.example.demo.repository.PostRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class PostServiceTestConfiguration {

  @Bean
  public PostService postService(PostRepository postRepository) {
    return new PostService(postRepository);
  }
}
