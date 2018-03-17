package com.asiafrank.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author zhangxf created at 2/26/2018.
 */
public class DeleteJobMain {
    public static void main(String[] args) {
        try {
            JobDetail job = JobBuilder.newJob(HelloJob.class)
                                       .withIdentity("helloJob", "group1")
                                       .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                                            .withIdentity("simpleTrigger", "group1")
                                            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
                                            .forJob(job)
                                            .build();
            Scheduler sch = new StdSchedulerFactory().getScheduler();
            sch.start();
            sch.deleteJob(job.getKey());
        } catch (ObjectAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
