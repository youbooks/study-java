
import java.util.concurrent.Phaser;

public class PhaserDemo
{
	public static void main(String[] args)
	{
		Phaser phaser = new Phaser(1);
		
		new Worker(phaser, "waiter").start();
		new Worker(phaser, "cook").start();
		new Worker(phaser, "chuancai").start();
		
		for(int i=1; i<=3; i++)
		{
			phaser.arriveAndAwaitAdvance();
			System.out.println("order :"+i+" finish...");
		}
		
		phaser.arriveAndDeregister();
	}
}
class Worker extends Thread
{
	private Phaser phaser;
	
	public Worker(Phaser phaser, String name)
	{
		setName(name);
		this.phaser = phaser;
		this.phaser.register();
	}
	@Override
	public void run()
	{
		for(int i=1; i<=3; i++)
		{
			System.out.println("current order : "+i+" : "+getName());
			if(i == 3)
				this.phaser.arriveAndDeregister();
			else
				this.phaser.arriveAndAwaitAdvance();
			
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}