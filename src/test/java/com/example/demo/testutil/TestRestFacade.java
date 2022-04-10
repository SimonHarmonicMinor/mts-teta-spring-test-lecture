package com.example.demo.testutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@TestComponent
public class TestRestFacade {

  @Autowired
  private Environment environment;
  @Autowired
  private TestRestTemplate testRestTemplate;

  public <T> ResponseEntity<T> exchange(
      String path,
      HttpMethod method,
      Object body,
      Class<T> responseType
  ) {
    return testRestTemplate.exchange(
        "http://localhost:" + environment.getProperty("local.server.port") + path,
        method,
        new HttpEntity<>(body),
        responseType
    );
  }
}
