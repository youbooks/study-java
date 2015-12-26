package com.lzyer.bingfa.demo;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * scheduleAtFixedRate:
 * Creates and executes a periodic action that becomes enabled 
 * first after the given initial delay, and subsequently with 
 * the given period; that is executions will commence after 
 * initialDelay then initialDelay+period, then initialDelay + 2 * period,
 * and so on. If any execution of the task encounters an exception,
 * subsequent executions are suppressed. Otherwise,
 * the task will only terminate via cancellation or termination of the executor. 
 * If any execution of this task takes longer than its period, 
 * then subsequent executions may start late, but will not concurrently execute.
 * 
 * scheduleWithFixedDelay:
 * Creates and executes a periodic action that becomes enabled first after 
 * the given initial delay, and subsequently with the given delay between 
 * the termination of one execution and the commencement of the next. If 
 * any execution of the task encounters an exception, subsequent executions 
 * are suppressed. Otherwise, the task will only terminate via cancellation 
 * or termination of the executor.
 * 
 * @author lzyer
 * @date 2015/12/26
 *
 */
public class ScheduledThreadPoolExecutorTest
{
	public static void main(String[] args)
	{
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
		
		for(int i=0; i<3; i++)
		{
			Runnable run = new Runnable()
			{
				@Override
				public void run()
				{
					System.out.println(Thread.currentThread().getName()+" run...");
				}
			};
			//executor.schedule(run, 1, TimeUnit.SECONDS); 延迟1秒后执行
			
			//executor.scheduleAtFixedRate(run, 1, 2, TimeUnit.SECONDS);
			executor.scheduleWithFixedDelay(run, 1, 1, TimeUnit.SECONDS);
		}
		
		
		//executor.shutdown();
	}
}
