package com.asiafrank.quartz;

import org.quartz.*;

import java.util.List;

/**
 * @author zhangxf created at 2/27/2018.
 */
public class ScheduleHelper {

    public static void reschedule(Scheduler scheduler, JobKey jobKey, Class<? extends org.quartz.Job> clazz, Trigger trigger) throws SchedulerException {
        JobBuilder jobBuilder = JobBuilder.newJob(clazz).withIdentity(jobKey);
        if (!scheduler.checkExists(jobKey)) {
            // if the job doesn't already exist, we can create it, along with its trigger. this prevents us
            // from creating multiple instances of the same job when running in a clustered environment
            scheduler.scheduleJob(jobBuilder.build(), trigger);
            System.out.println("SCHEDULED JOB WITH KEY " + jobKey.toString());
        } else {
            // if the job has exactly one trigger, we can just reschedule it, which allows us to update the schedule for
            // that trigger.
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            if (triggers.size() == 1) {
                scheduler.rescheduleJob(triggers.get(0).getKey(), trigger);
                return;
            }

            // if for some reason the job has multiple triggers, it's easiest to just delete and re-create the job,
            // since we want to enforce a one-to-one relationship between jobs and triggers
            scheduler.unscheduleJob(trigger.getKey());
            scheduler.scheduleJob(jobBuilder.build(), trigger);
        }
    }
}
