package com.example.demo.service.exception;

public class PostExistsException extends RuntimeException {

  public PostExistsException(String message) {
    super(message);
  }

  public PostExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
