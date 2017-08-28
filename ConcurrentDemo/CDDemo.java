

import java.util.concurrent.CountDownLatch;

public class CDDemo
{
	public static void main(String[] args)
	{
		CountDownLatch cd = new CountDownLatch(3);
	
		new Racer(cd, "A").start();
		new Racer(cd, "B").start();
		new Racer(cd, "C").start();
		
		for(int i=0; i<3; i++)
		{
			try
			{
				Thread.sleep(1000);
				cd.countDown();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			System.out.println(i);
		
			if(i == 2)
				System.out.println("start");
		}
		
	}
}
class Racer extends Thread
{
	private CountDownLatch countDownLatch;
	
	public Racer(CountDownLatch countDownLatch, String name)
	{
		setName(name);
		this.countDownLatch = countDownLatch;
	}
	@Override
	public void run()
	{
		try
		{
				countDownLatch.await();
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		for(int i=0; i<3; i++)
		{
			System.out.println(getName()+" : "+i);
		}
	}
}

