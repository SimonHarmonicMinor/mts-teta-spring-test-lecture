package com.example.demo.service;

import static java.lang.String.format;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.exception.PostExistsException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Transactional
  public List<PostResponse> createPosts(List<PostCreateCommand> commands) {
    return commands.stream()
        .map(this::createPost)
        .collect(Collectors.toList());
  }

  @Transactional
  public PostResponse createPost(PostCreateCommand command) {
    if (postRepository.existsByTitle(command.getTitle())) {
      throw new PostExistsException(
          format("Post with title '%s' is already present", command.getTitle())
      );
    }
    Post post = postRepository.save(Post.newPost(command.getTitle(), command.getContent()));
    return new PostResponse(post.getId(), post.getTitle(), post.getContent());
  }
}
