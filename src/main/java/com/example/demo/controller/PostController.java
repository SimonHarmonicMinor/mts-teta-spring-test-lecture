package com.example.demo.controller;

import com.example.demo.service.PostCreateCommand;
import com.example.demo.service.PostResponse;
import com.example.demo.service.PostService;
import com.example.demo.service.exception.PostExistsException;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@Validated
public class PostController {

  private static final Logger LOG = LoggerFactory.getLogger(PostController.class);

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping
  public ResponseEntity<PostResponse> createPost(
      @NotNull(message = "Title is null")
      @Size(max = 20, message = "Title is too long")
      @RequestParam String title,

      @NotNull
      @RequestParam String content) {
    try {
      return new ResponseEntity<>(
          postService.createPost(new PostCreateCommand(title, content)),
          HttpStatus.CREATED
      );
    } catch (PostExistsException e) {
      throw new ControllerException(
          String.format("Post with title '%s' already exists", title),
          e,
          HttpStatus.CONFLICT
      );
    }
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleControllerException(ControllerException e) {
    LOG.error("Error during request processing", e);
    return new ResponseEntity<>(
        new ErrorResponse(e.getMessage()),
        e.getHttpStatus()
    );
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException e
  ) {
    LOG.error("Error during parameters validation", e);
    return new ResponseEntity<>(
        new ErrorResponse(e.getMessage()),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleException(
      Exception e
  ) {
    LOG.error("Unknown error during request processing", e);
    return new ResponseEntity<>(
        new ErrorResponse(e.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR
    );
  }
}
