package com.asiafrank.learn.spring.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * AbstractCustom
 * <p>
 * </p>
 * Created at 1/16/2017.
 *
 * @author zhangxf
 */
public abstract class AbstractCustom {
    private MongoOperations mongoOperations;

    public MongoOperations getOperations() {
        return mongoOperations;
    }

    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}
