

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo
{
	public static void main(String[] args)
	{
		new MT().start();
		new MT().start();
		new MT().start();
		new MT().start();
	}
}
class Data
{
	static int i=0;
	static Lock lock = new ReentrantLock();
	static AtomicInteger ai = new AtomicInteger(0);
	static void operator()
	{
		System.out.println(ai.incrementAndGet());
	/*	lock.lock();
		i++;
		System.out.println(i);
		lock.unlock();*/
	}
}
class MT extends Thread
{
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			Data.operator();
		}
	}
}