package com.asiafrank.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author zhangxf created at 2/26/2018.
 */
public class RescheduleMain {
    public static void main(String[] args) {
        try {
            JobDetail job = JobBuilder.newJob(HelloJob.class)
                                      .withIdentity("helloJob", "group1")
                                      .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                                            .withIdentity("simpleTrigger", "group1")
                                            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
                                            .forJob(job)
                                            .build();
            Scheduler sch = new StdSchedulerFactory().getScheduler();
            sch.start();
            Thread.sleep(10000L);

            System.out.println("reschedule");

            // reschedule
            sch.rescheduleJob(trigger.getKey(), trigger);
            /*
            rescheduleJob
            当 schedule.start() 后，马上 reschedule，有很大概率会长生多个 fired_trigger，然后多个节点执行同一个任务
            当 schedule.start() 后，休眠一段时间，再 reschedule，只有一个节点能执行任务，并且更新了 trigger
            qrtz_fired_triggers 如果有多个一样的 trigger 就表明不起作用
             */
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
