

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class FJDemo
{
	public static void main(String[] args)throws Exception
	{
		ForkJoinPool pool = new ForkJoinPool();
		Future<Long> result = pool.submit(new MTask(0, 10001));
		System.out.println(result.get());
		pool.shutdown();
	}
}

class MTask extends RecursiveTask<Long>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final int THREHOLD = 1000;

	private int begin, end;
	
	public MTask(int begin, int end)
	{
		this.begin = begin;
		this.end = end;
	}
	@Override
	protected Long compute()
	{
		long sum = 0;
		if((end - begin)<THREHOLD)
		{
			for(int i = begin; i<=end; i++)
			{
				sum += i;
			}
		}else
		{
			int mid = (begin +end)/2;
			
			MTask left = new MTask(begin, mid);
			left.fork();
			
			MTask right = new MTask(mid+1, end);
			right.fork();
			
			Long lr = left.join();
			System.out.println(begin +" - "+mid+" : "+lr);
			Long rr = right.join();
			System.out.println(mid+1 +" - "+end+" : "+rr);
			sum = lr + rr;
		}
		return sum;
	}
	
}