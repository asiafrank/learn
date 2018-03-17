package com.asiafrank.learn.spring.repo;

import com.asiafrank.learn.spring.model.Sample;
import org.springframework.data.mongodb.repository.Query;

/**
 * SampleRepo
 * <p>
 * </p>
 * Created at 12/23/2016.
 *
 * @author asiafrank
 */
public interface SampleRepo<T extends Sample, ID extends String> extends SampleRepoCustom, BaseRepo<T, ID> {
    // Declare query methods here
    @Query("{'name' : ?0}")
    Sample find(String name);
}
