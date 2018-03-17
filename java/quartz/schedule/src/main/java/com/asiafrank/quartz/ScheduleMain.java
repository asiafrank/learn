package com.asiafrank.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * BUG: https://github.com/quartz-scheduler/quartz/issues/29
 * @author zhangxf created at 2/26/2018.
 */
public class ScheduleMain {
    public static void main(String[] args) {
        try {
            JobDetail job = JobBuilder.newJob(HelloJob.class)
                                       .withIdentity("helloJob", "group1")
                                       .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                                            .withIdentity("simpleTrigger", "group1")
                                            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))
                                            .forJob(job)
                                            .build();
            Scheduler sch = new StdSchedulerFactory().getScheduler();
            sch.start();

            Thread.sleep(10000L);
            System.out.println("schedule check");
            if (sch.checkExists(trigger.getKey())) {
                System.out.println("exists");
            } else {
                sch.scheduleJob(job, trigger);
            }
        } catch (ObjectAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("end");
        }
    }
}
