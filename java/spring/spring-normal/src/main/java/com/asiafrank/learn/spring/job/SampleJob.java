package com.asiafrank.learn.spring.job;

import com.asiafrank.learn.spring.model.Sample;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * SampleJob
 * <p>
 * ref: http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-03.html
 * </p>
 * Created at 12/26/2016.
 *
 * @author asiafrank
 */
public class SampleJob implements Job {
    @Override
    @SuppressWarnings("unchecked")
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jdm = context.getJobDetail().getJobDataMap();
        List<Sample> samples = (List<Sample>) jdm.get("samples");
        // do something with samples
    }
}
