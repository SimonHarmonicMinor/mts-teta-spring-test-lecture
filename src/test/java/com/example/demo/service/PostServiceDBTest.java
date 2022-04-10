package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.IntegrationSuite;
import com.example.demo.entity.Post;
import com.example.demo.service.exception.PostExistsException;
import com.example.demo.testutil.DBTest;
import com.example.demo.testutil.TestDBFacade;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@DBTest
@ContextConfiguration(classes = PostServiceTestConfiguration.class)
class PostServiceDBTest extends IntegrationSuite {

  @Autowired
  private PostService postService;
  @Autowired
  private TestDBFacade db;

  @BeforeEach
  void beforeEach() {
    db.cleanDatabase();
  }

  @Test
  void shouldCreateMultiplePosts() {
    assertDoesNotThrow(
        () -> postService.createPosts(
            List.of(
                new PostCreateCommand("title1", "content1"),
                new PostCreateCommand("title2", "content2"),
                new PostCreateCommand("title3", "content3")
            )
        )
    );

    assertEquals(3, db.count(Post.class));
  }

  @Test
  void shouldRollbackIfAnyPostCreationFails() {
    String title = "title";
    String content = "content";
    db.persist(Post.newPost(title, content));

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

    long count = db.count(Post.class);
    assertEquals(1, count);
  }
}