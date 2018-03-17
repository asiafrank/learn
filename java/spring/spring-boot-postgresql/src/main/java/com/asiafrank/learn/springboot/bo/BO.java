package com.asiafrank.learn.springboot.bo;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface BO<T, ID extends Serializable, R extends JpaRepository<T, ID>> {

	T findOne(ID id);

	<S extends T> S findOne(Example<S> example);

	List<T> findAll();

	List<T> findAll(Sort sort);

	List<T> findAll(Iterable<ID> ids);

	Page<T> findAll(Pageable pageable);

	<S extends T> List<S> findAll(Example<S> example);

	<S extends T> List<S> findAll(Example<S> example, Sort sort);

	<S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends T> List<S> save(Iterable<S> entities);

	<S extends T> S save(S entity);

	void flush();

	<S extends T> S saveAndFlush(S entity);

	boolean exists(ID id);

	<S extends T> boolean exists(Example<S> example);

	long count();

	<S extends T> long count(Example<S> example);

	void delete(ID id);

	void delete(T entity);

	void delete(Iterable<? extends T> entities);

	void deleteAll();

	void deleteInBatch(Iterable<T> entities);

	void deleteAllInBatch();
}