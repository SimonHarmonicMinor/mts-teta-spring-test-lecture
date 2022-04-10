package com.example.demo.controller;

import org.springframework.http.HttpStatus;

public class ControllerException extends RuntimeException {
  private final HttpStatus httpStatus;

  public ControllerException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public ControllerException(String message, Throwable cause,
      HttpStatus httpStatus) {
    super(message, cause);
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
