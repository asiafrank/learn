package com.asiafrank.learn.springboot.core.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BO<T, VO, PK extends Serializable> {
    void insert(T entity);

    T get(VO condition);
    T get(PK id);

    List<T> find();
    List<T> find(OrderBy orderBy);
    List<T> find(VO condition);
    List<T> find(VO condition, OrderBy orderBy);

    Page<T> find(Pageable pageable);
    Page<T> find(OrderBy orderBy, Pageable pageable);
    Page<T> find(VO condition, Pageable pageable);
    Page<T> find(VO condition, OrderBy orderBy, Pageable pageable);

    List<T> findByIdList(List<PK> idList, VO condition, OrderBy orderBy);
    List<T> findByIdList(List<PK> idList, VO condition);
    List<T> findByIdList(List<PK> idList, OrderBy orderBy);
    List<T> findByIdList(List<PK> idList);

    int count();
    int count(String column);
    int count(VO condition);
    int count(VO condition, String column);

    Map<String, Object> aggregate(String[] functions, String[] columns);
    Map<String, Object> aggregate(VO condition, String[] functions, String[] columns);

    int update(T entity, VO condition);
    int update(T entity, PK id);
    int updateByIdList(T entity, List<PK> idList, VO condition);
    int updateByIdList(T entity, List<PK> idList);

    int update(Map<String, Object> entity, VO condition);
    int update(Map<String, Object> entity, PK id);
    int updateByIdList(Map<String, Object> entity, List<PK> idList, VO condition);
    int updateByIdList(Map<String, Object> entity, List<PK> idList);

    int remove(VO condition);
    int remove(PK id);
    int removeByIdList(List<PK> idList, VO condition);
    int removeByIdList(List<PK> idList);
}
