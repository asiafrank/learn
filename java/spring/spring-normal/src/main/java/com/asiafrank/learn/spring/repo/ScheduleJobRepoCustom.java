package com.asiafrank.learn.spring.repo;

import com.asiafrank.learn.spring.model.ScheduleJob;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * SampleRepoCustom
 * <p>
 * </p>
 * Created at 12/23/2016.
 *
 * @author asiafrank
 */
@NoRepositoryBean
public interface ScheduleJobRepoCustom {
    ScheduleJob customMethod();
}
