package com.asiafrank.quartz;

import org.quartz.*;

/**
 * @author zhangxf created at 2/26/2018.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class HelloJob implements Job {

    private static int count = 0;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("****Hello Job****{" + count + "}");
        ++count;
    }
}
