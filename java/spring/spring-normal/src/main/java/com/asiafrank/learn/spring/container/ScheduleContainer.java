package com.asiafrank.learn.spring.container;

import com.asiafrank.learn.spring.bo.ScheduleJobBO;
import com.asiafrank.learn.spring.factory.BOFactory;
import com.asiafrank.learn.spring.job.SampleJob;
import com.asiafrank.learn.spring.model.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

/**
 * ScheduleContainer
 * <p>
 * TODO: change schedule cron save to db and newTrigger to replace the original one
 * ref: http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-03.html
 * http://www.quartz-scheduler.org/documentation/quartz-2.x/cookbook/UpdateTrigger.html
 * </p>
 * Created at 12/26/2016.
 *
 * @author asiafrank
 */
public final class ScheduleContainer {
    private final static Logger log = LoggerFactory.getLogger(ScheduleContainer.class);

    private final static Properties config = getConfig();

    private final static StdSchedulerFactory schedulerFactory = getStdSchedulerFactory(config);

    private final static Scheduler scheduler = getScheduler(schedulerFactory);

    private final static ScheduleJobBO scheduleJobBO = BOFactory.instance().getScheduleJobBO();

    /**
     * key: scheduleJob Id
     * value: scheduleJob instance
     */
    private final static Map<String, ScheduleJob> container = new LinkedHashMap<>();

    public void init() {
        try {
            List<ScheduleJob> scheduleJobs = scheduleJobBO.findAll(true);
            if (scheduleJobs == null || scheduleJobs.isEmpty()) {
                return;
            }

            scheduler.start();

            scheduleJobs.forEach(ScheduleContainer::schedule);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Schedule job
     *
     * @param job {@link ScheduleJob}
     * @return the first fire time of the scheduled trigger
     */
    public static Date schedule(ScheduleJob job) {
        Date date = null;

        JobDataMap jdm = new JobDataMap();
        jdm.put("examples", job.getSamples());

        // define the job and tie it to our HelloJob class
        JobKey jobKey = jobKey(job.getIdentityName(), job.getIdentityGroup());
        JobDetail detail = newJob(SampleJob.class)
                .withIdentity(jobKey)
                .setJobData(jdm)
                .build();

        // Trigger the job to run now, and then every 40 seconds
        TriggerKey triggerKey = triggerKey(job.getTriggerName(), job.getTriggerGroup());
        CronTrigger trigger = newTrigger()
                .withIdentity(triggerKey)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();

        // Tell quartz to schedule the job using our trigger
        try {
            date = scheduler.scheduleJob(detail, trigger);
            container.put(job.getId(), job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Updating an existing trigger
     *
     * @param job {@link ScheduleJob}
     * @return <code>null</code> if a <code>Trigger</code> with the given
     *         name & group was not found and removed from the store (and the
     *         new trigger is therefore not stored), otherwise
     *         the first fire time of the newly scheduled trigger is returned.
     */
    public static Date reschedule(ScheduleJob job) {
        Date date = null;
        try {
            TriggerKey triggerKey = triggerKey(job.getTriggerName(), job.getTriggerGroup());

            // retrieve the trigger
            Trigger oldTrigger = scheduler.getTrigger(triggerKey);

            // obtain a builder that would produce the trigger
            TriggerBuilder tb = oldTrigger.getTriggerBuilder();

            // update the schedule associated with the builder, and build the new trigger
            // (other builder methods could be called, to change the trigger in any desired way)
            Trigger newTrigger = tb.withSchedule(CronScheduleBuilder
                    .cronSchedule(job.getCron())).build();

            date = scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Deleting a Job from {@link #scheduler} and Unscheduling All of Its Triggers
     *
     * @param job {@link ScheduleJob}
     * @return <code>true</code>, success; <code>false</code>, failed
     */
    public static boolean unschedule(ScheduleJob job) {
        try {
            container.remove(job.getId());
            JobKey jobKey = jobKey(job.getIdentityName(), job.getIdentityGroup());
            return scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get scheduleJob list
     *
     * @return unmodifiableList of ScheduleJobs
     */
    public static List<ScheduleJob> getScheduleJobs() {
        List<ScheduleJob> list = new LinkedList<>();
        container.forEach((k, v)-> list.add(v));
        return Collections.unmodifiableList(list);
    }

    //========================================================================
    // Init Utils
    //========================================================================

    private static Properties getConfig() {
        Resource resource = new ClassPathResource("schedule.properties");
        try {
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Scheduler getScheduler(StdSchedulerFactory stdFactory) {
        if (scheduler == null) {
            Scheduler s = null;
            try {
                s = stdFactory.getScheduler();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            return s;
        }
        return scheduler;
    }

    private static StdSchedulerFactory getStdSchedulerFactory(Properties cfg) {
        StdSchedulerFactory stdFactory;
        try {
            stdFactory = new StdSchedulerFactory(cfg);
        } catch (SchedulerException e) {
            e.printStackTrace();
            stdFactory = new StdSchedulerFactory();
        }
        return stdFactory;
    }
}
