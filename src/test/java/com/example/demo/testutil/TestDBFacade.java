package com.example.demo.testutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

@TestComponent
public class TestDBFacade {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private TransactionTemplate transactionTemplate;
  @Autowired
  private TestEntityManager testEntityManager;

  public void cleanDatabase() {
    transactionTemplate.executeWithoutResult(status -> {
      JdbcTestUtils.deleteFromTables(jdbcTemplate, "post");
    });
  }

  public <T> T persist(T t) {
    return transactionTemplate.execute(
        status -> testEntityManager.persistAndFlush(t)
    );
  }

  public <T> long count(Class<T> clazz) {
    return transactionTemplate.execute(status -> (long) testEntityManager.getEntityManager()
        .createQuery("SELECT COUNT(e) FROM " + clazz.getName() + " e")
        .getSingleResult());
  }

  public boolean exists(Class<?> clazz, Object id) {
    return transactionTemplate.execute(
        status -> testEntityManager.find(clazz, id) != null
    );
  }
}
