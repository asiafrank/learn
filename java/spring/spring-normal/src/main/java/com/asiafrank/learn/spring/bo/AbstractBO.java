package com.asiafrank.learn.spring.bo;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.List;

/**
 * AbstractBO
 * <p>
 * </p>
 * Created at 28/12/2016.
 *
 * @author asiafrank
 */
public abstract class AbstractBO<T, ID extends Serializable, R extends MongoRepository<T, ID>> implements BO<T, ID , R> {
    protected abstract R getRepo();

    @Override
    public <S extends T> S save(S entity) {
        return getRepo().save(entity);
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> entities) {
        return getRepo().save(entities);
    }

    @Override
    public <S extends T> S insert(S entity) {
        return getRepo().insert(entity);
    }

    @Override
    public <S extends T> List<S> insert(Iterable<S> entities) {
        return getRepo().insert(entities);
    }

    @Override
    public T findOne(ID id) {
        return getRepo().findOne(id);
    }

    @Override
    public <S extends T> S findOne(Example<S> example) {
        return getRepo().findOne(example);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return getRepo().findAll(ids);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return getRepo().findAll(sort);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return getRepo().findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return getRepo().findAll(example, sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepo().findAll(pageable);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return getRepo().findAll(example, pageable);
    }

    @Override
    public long count() {
        return getRepo().count();
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return getRepo().count(example);
    }

    @Override
    public void delete(ID id) {
        getRepo().delete(id);
    }

    @Override
    public void delete(T entity) {
        getRepo().delete(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        getRepo().delete(entities);
    }

    @Override
    public void deleteAll() {
        getRepo().deleteAll();
    }

    @Override
    public boolean exists(ID id) {
        return getRepo().exists(id);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return getRepo().exists(example);
    }
}
