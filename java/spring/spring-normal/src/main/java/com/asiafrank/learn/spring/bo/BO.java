package com.asiafrank.learn.spring.bo;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.List;

/**
 * BO
 * <p>
 * </p>
 * Created at 28/12/2016.
 *
 * @author asiafrank
 */
public interface BO<T, ID extends Serializable, R extends MongoRepository<T, ID>> {

    <S extends T> S save(S entity);

    <S extends T> List<S> save(Iterable<S> entities);

    <S extends T> S insert(S entity);

    <S extends T> List<S> insert(Iterable<S> entities);

    T findOne(ID id);

    <S extends T> S findOne(Example<S> example);

    List<T> findAll();

    Iterable<T> findAll(Iterable<ID> ids);

    List<T> findAll(Sort sort);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    Page<T> findAll(Pageable pageable);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    long count();

    <S extends T> long count(Example<S> example);

    void delete(ID id);

    void delete(T entity);

    void delete(Iterable<? extends T> entities);

    void deleteAll();

    boolean exists(ID id);

    <S extends T> boolean exists(Example<S> example);
}
