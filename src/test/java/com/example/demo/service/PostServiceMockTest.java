package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.demo.entity.Post;
import com.example.demo.repository.InMemoryPostRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.exception.PostExistsException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceMockTest {

  @Mock
  private PostRepository postRepository;

  @Test
  void shouldCreatePost() {
    PostService postService = new PostService(postRepository);
    String title = "some_title";
    String content = "some_content";
    when(postRepository.existsByTitle(title))
        .thenReturn(false);
    when(postRepository.save(any()))
        .thenAnswer(invocationOnMock -> {
          Post argument = invocationOnMock.getArgument(0, Post.class);
          argument.setId(1L);
          return argument;
        });

    PostResponse postResponse = postService.createPost(new PostCreateCommand(title, content));

    assertEquals(title, postResponse.getTitle());
    assertEquals(content, postResponse.getContent());
    assertNotNull(postResponse.getId());
  }

  @Test
  void shouldCreatePostV2() {
    PostService postService = new PostService(new InMemoryPostRepository());
    String title = "some_title";
    String content = "some_content";

    PostResponse postResponse = postService.createPost(new PostCreateCommand(title, content));

    assertEquals(title, postResponse.getTitle());
    assertEquals(content, postResponse.getContent());
    assertNotNull(postResponse.getId());
  }

  @Test
  void shouldFailIfPostIsAlreadyPresent() {
    PostRepository memoryPostRepository = new InMemoryPostRepository();
    PostService postService = new PostService(memoryPostRepository);
    String title = "some_title";
    String content = "some_content";
    memoryPostRepository.save(Post.newPost(title, content));

    assertThrows(
        PostExistsException.class,
        () -> postService.createPost(new PostCreateCommand(title, content))
    );

    long count = memoryPostRepository.count();
    assertEquals(1, count);
  }

  @Test
  void shouldRollbackIfAnyPostCreationFails() {
    PostRepository memoryPostRepository = new InMemoryPostRepository();
    PostService postService = new PostService(memoryPostRepository);
    String title = "some_title";
    String content = "some_content";
    memoryPostRepository.save(Post.newPost(title, content));

    assertThrows(
        PostExistsException.class,
        () -> postService.createPosts(
            List.of(
                new PostCreateCommand("title1", "content1"),
                new PostCreateCommand("title2", "content2"),
                new PostCreateCommand(title, content)
            )
        )
    );

    long count = memoryPostRepository.count();
    assertEquals(1, count);
  }
}