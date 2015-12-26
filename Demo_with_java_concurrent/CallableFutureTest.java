import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFutureTest
{
	public static void main(String[] args)
	{
		ExecutorService service = Executors.newCachedThreadPool();
		
		Task task = new Task();
		
		Future<Integer> future = service.submit(task);
		
		service.shutdown();
		
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("main thread run ....");
		
		try
		{
			System.out.println("result: "+future.get());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("main end.....");
	}
}
class Task implements Callable<Integer>
{
	@Override
	public Integer call() throws Exception
	{
		System.out.println("sub thread compute...");
		Thread.sleep(3000);
		int sum = 0;
		for(int i=1; i<=100; i++)
		{
			sum+=i;
		}
		return sum;
	}
}

















