package com.asiafrank.learn.spring.bo;


import com.asiafrank.learn.spring.model.ScheduleJob;
import com.asiafrank.learn.spring.repo.ScheduleJobRepo;

import java.util.List;

/**
 * ScheduleJobBO
 * <p>
 * </p>
 * Created at 28/12/2016.
 *
 * @author asiafrank
 */
public interface ScheduleJobBO extends BO<ScheduleJob, String, ScheduleJobRepo<ScheduleJob, String>> {
	List<ScheduleJob> find(String name);

	List<ScheduleJob> findAll(boolean enable);
}
