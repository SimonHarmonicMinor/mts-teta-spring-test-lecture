package com.example.demo.repository;

import com.example.demo.entity.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

public class InMemoryPostRepository implements PostRepository {

  private final AtomicLong idGenerator = new AtomicLong(0);
  private final Map<Long, Post> posts = new ConcurrentHashMap<>();

  @Override
  public boolean existsByTitle(String title) {
    return posts.values()
        .stream()
        .anyMatch(post -> Objects.equals(post.getTitle(), title));
  }

  @Override
  public List<Post> findAll() {
    return new ArrayList<>(posts.values());
  }

  @Override
  public List<Post> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Post> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Post> findAllById(Iterable<Long> longs) {
    return null;
  }

  @Override
  public long count() {
    return posts.size();
  }

  @Override
  public void deleteById(Long aLong) {
    posts.remove(aLong);
  }

  @Override
  public void delete(Post entity) {
    deleteById(entity.getId());
  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends Post> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Post save(Post entity) {
    long id = idGenerator.incrementAndGet();
    entity.setId(id);
    posts.put(id, entity);
    return entity;
  }

  @Override
  public <S extends Post> List<S> saveAll(Iterable<S> entities) {
    List<Post> posts = new ArrayList<>();
    for (S entity : entities) {
      posts.add(save(entity));
    }
    return (List<S>) posts;
  }

  @Override
  public Optional<Post> findById(Long aLong) {
    return Optional.ofNullable(posts.get(aLong));
  }

  @Override
  public boolean existsById(Long aLong) {
    return posts.containsKey(aLong);
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Post> S saveAndFlush(S entity) {
    return (S) save(entity);
  }

  @Override
  public <S extends Post> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Post> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Post getOne(Long aLong) {
    return null;
  }

  @Override
  public Post getById(Long aLong) {
    return null;
  }

  @Override
  public <S extends Post> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Post> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Post> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Post> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends Post, R> R findBy(Example<S> example,
      Function<FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }
}