
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环屏障,指定线程的数量同时到达某个点，执行某个事件。例如：斗地主
 *
 */
public class CBDemo
{
	public static void main(String[] args)
	{
		CyclicBarrier barrier = new CyclicBarrier(3, new Runnable()
		{
			
			@Override
			public void run()
			{
				System.out.println("Game start");
			}
		});
		new Player(barrier, "A").start();
		new Player(barrier, "B").start();
		new Player(barrier, "C").start();
	}
}

class Player extends Thread
{
	private CyclicBarrier barrier;
	
	public Player(CyclicBarrier barrier, String name)
	{
		this.barrier = barrier;
		setName(name);
	}
	@Override
	public void run()
	{
		System.out.println(getName()+" wait other players....");
		try
		{
			this.barrier.await();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (BrokenBarrierException e)
		{
			e.printStackTrace();
		}
	}
}
