package com.example.appoperation.db.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractBO<T, VO, PK extends Serializable> implements BO<T, VO, PK> {
    protected abstract DAO<T, VO, PK> getDAO();

    @Override
    public void insert(T entity) {
        getDAO().insert(entity);
    }

    @Override
    public T get(VO condition) {
        return getDAO().get(condition);
    }

    @Override
    public T get(PK id) {
        return getDAO().get(id);
    }

    @Override
    public List<T> find() {
        return getDAO().find();
    }

    @Override
    public List<T> find(OrderBy orderBy) {
        return getDAO().find(orderBy);
    }

    @Override
    public List<T> find(VO condition) {
        return getDAO().find(condition);
    }

    @Override
    public List<T> find(VO condition, OrderBy orderBy) {
        return getDAO().find(condition, orderBy);
    }

    @Override
    public Page<T> find(Pageable pageable) {
        return getDAO().find(pageable);
    }

    @Override
    public Page<T> find(OrderBy orderBy, Pageable pageable) {
        return getDAO().find(orderBy, pageable);
    }

    @Override
    public Page<T> find(VO condition, Pageable pageable) {
        return getDAO().find(condition, pageable);
    }

    @Override
    public Page<T> find(VO condition, OrderBy orderBy, Pageable pageable) {
        return getDAO().find(condition, orderBy, pageable);
    }

    @Override
    public List<T> findByIdList(List<PK> idList, VO condition, OrderBy orderBy) {
        return getDAO().findByIdList(idList, condition, orderBy);
    }

    @Override
    public List<T> findByIdList(List<PK> idList, VO condition) {
        return getDAO().findByIdList(idList, condition);
    }

    @Override
    public List<T> findByIdList(List<PK> idList, OrderBy orderBy) {
        return getDAO().findByIdList(idList, orderBy);
    }

    @Override
    public List<T> findByIdList(List<PK> idList) {
        return getDAO().findByIdList(idList);
    }

    @Override
    public int count() {
        return getDAO().count();
    }

    @Override
    public int count(String column) {
        return getDAO().count(column);
    }

    @Override
    public int count(VO condition) {
        return getDAO().count(condition);
    }

    @Override
    public int count(VO condition, String column) {
        return getDAO().count(condition, column);
    }

    @Override
    public Map<String, Object> aggregate(String[] functions, String[] columns) {
        return getDAO().aggregate(functions, columns);
    }

    @Override
    public Map<String, Object> aggregate(VO condition, String[] functions, String[] columns) {
        return getDAO().aggregate(condition, functions, columns);
    }

    @Override
    public int update(T entity, VO condition) {
        return getDAO().update(entity, condition);
    }

    @Override
    public int update(T entity, PK id) {
        return getDAO().update(entity, id);
    }

    @Override
    public int updateByIdList(T entity, List<PK> idList, VO condition) {
        return getDAO().updateByIdList(entity, idList, condition);
    }

    @Override
    public int updateByIdList(T entity, List<PK> idList) {
        return getDAO().updateByIdList(entity, idList);
    }

    @Override
    public int update(Map<String, Object> entity, VO condition) {
        return getDAO().update(entity, condition);
    }

    @Override
    public int update(Map<String, Object> entity, PK id) {
        return getDAO().update(entity, id);
    }

    @Override
    public int updateByIdList(Map<String, Object> entity, List<PK> idList, VO condition) {
        return getDAO().updateByIdList(entity, idList, condition);
    }

    @Override
    public int updateByIdList(Map<String, Object> entity, List<PK> idList) {
        return getDAO().updateByIdList(entity, idList);
    }

    @Override
    public int remove(VO condition) {
        return getDAO().remove(condition);
    }

    @Override
    public int remove(PK id) {
        return getDAO().remove(id);
    }

    @Override
    public int removeByIdList(List<PK> idList, VO condition) {
        return getDAO().removeByIdList(idList, condition);
    }

    @Override
    public int removeByIdList(List<PK> idList) {
        return getDAO().removeByIdList(idList);
    }
}
