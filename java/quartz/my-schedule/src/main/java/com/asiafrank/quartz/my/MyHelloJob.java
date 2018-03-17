package com.asiafrank.quartz.my;

import org.quartz.*;

/**
 * @author zhangxf created at 2/26/2018.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyHelloJob implements Job {

    private static int count = 0;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("****My Hello Job****{" + count + "}");
        ++count;
    }
}
