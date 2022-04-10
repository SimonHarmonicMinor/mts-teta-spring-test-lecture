package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.IntegrationSuite;
import com.example.demo.entity.Post;
import com.example.demo.service.PostResponse;
import com.example.demo.testutil.E2ETest;
import com.example.demo.testutil.TestDBFacade;
import com.example.demo.testutil.TestRestFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@E2ETest
class PostControllerE2ETest extends IntegrationSuite {

  @Autowired
  private TestRestFacade rest;
  @Autowired
  private TestDBFacade db;

  @BeforeEach
  void beforeEach() {
    db.cleanDatabase();
  }

  @Test
  void shouldCreatePost() {
    ResponseEntity<PostResponse> response = rest.exchange(
        String.format("/api/post?title=%s&content=%s", "some_title", "some_content"),
        HttpMethod.POST,
        null,
        PostResponse.class
    );

    assertTrue(response.getStatusCode().is2xxSuccessful());
    assertNotNull(response.getBody());
    PostResponse body = response.getBody();
    assertTrue(db.exists(Post.class, body.getId()));
  }
}