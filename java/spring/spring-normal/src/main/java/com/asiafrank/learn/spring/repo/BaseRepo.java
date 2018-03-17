package com.asiafrank.learn.spring.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * BaseRepo - Adding custom behavior to all repositories
 * <p>
 * </p>
 * Created at 22/12/2016.
 *
 * @author asiafrank
 */
@NoRepositoryBean
public interface BaseRepo<T, ID extends Serializable> extends MongoRepository<T, ID> {
    void sharedCustomMethod(ID id);
}
