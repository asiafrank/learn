package com.asiafrank.learn.spring.repo.impl;

import com.asiafrank.learn.spring.repo.BaseRepo;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;

/**
 * BaseRepoImpl
 * <p>
 * </p>
 * Created at 22/12/2016.
 *
 * @author asiafrank
 */
public class BaseRepoImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID>
        implements BaseRepo<T, ID>
{
    private final MongoOperations mongoOperations;

    public BaseRepoImpl(MongoEntityInformation<T, ID> metadata,
                        MongoOperations mongoOperations)
    {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void sharedCustomMethod(ID id) {
        System.out.println("shared custom method: " + id.toString());
        // implementation goes here
    }
}
