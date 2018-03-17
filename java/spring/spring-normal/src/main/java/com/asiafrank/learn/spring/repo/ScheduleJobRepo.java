package com.asiafrank.learn.spring.repo;

import com.asiafrank.learn.spring.model.ScheduleJob;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * SampleRepo
 * <p>
 * </p>
 * Created at 12/23/2016.
 *
 * @author asiafrank
 */
public interface ScheduleJobRepo<T extends ScheduleJob, ID extends String> extends ScheduleJobRepoCustom, BaseRepo<T, ID> {
    // Declare query methods here
    @Query("{'name' : ?0}")
    List<ScheduleJob> find(String name);

    @Query("{'enable': ?0}")
    List<ScheduleJob> findAll(boolean enable);
}
