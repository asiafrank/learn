package com.asiafrank.se.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 长时间任务
 * 
 * @author dream-victor
 * 
 */
public class LongTask implements Callable<String> {

	@Override
	public String call() throws Exception {
		TimeUnit.SECONDS.sleep(10);
		return "success";
	}
}