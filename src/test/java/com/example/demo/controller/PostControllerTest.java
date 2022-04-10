package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.service.PostCreateCommand;
import com.example.demo.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostController.class)
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private PostService postService;

  @Test
  void shouldReturn400IfTitleIsNull() throws Exception {

   mockMvc.perform(
            post("/api/post")
                .param("title", "a".repeat(30))
                .param("content", "content")
        ).andDo(print())
        .andExpect(status().isBadRequest());

    verify(postService, times(0)).createPost(any());
  }

  @Test
  void shouldReturn2xxIfPostIsSuccessfullyCreated() throws Exception {
    mockMvc.perform(
            post("/api/post")
                .param("title", "post_title")
                .param("content", "post_content")
        ).andDo(print())
        .andExpect(status().is2xxSuccessful());

    verify(postService, times(1)).createPost(eq(
        new PostCreateCommand("post_title", "post_content")
    ));
  }
}