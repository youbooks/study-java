package com.lzyer.bingfa.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * use multithread visit baidu websit
 * use thread pool
 * @author lzyer
 * @date 2015/12/26
 *
 */
public class ExecutorTest 
{

	private static final int THREAD_NUMBER = 100;
	
	private static final ExecutorService exec = Executors.newFixedThreadPool(THREAD_NUMBER);
	
	public static void main(String[] args)
	{
		for(int i=0; i<10000; i++){
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						URL url = new URL("http://www.baidu.com");
						HttpURLConnection conn = (HttpURLConnection)url.openConnection();
						StringBuffer sb = new StringBuffer();
						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")) ;
						String line = null;
						while(null !=(line = reader.readLine()))
						{
							sb.append(line);
							break;
						}
						System.out.println(sb.toString());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			};
			exec.execute(runnable);
		}
		exec.shutdown();
	}
}
