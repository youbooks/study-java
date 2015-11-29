
import java.util.concurrent.Semaphore;

public class SeDemo
{

	public static void main(String[] args)
	{
		Semaphore semaphore = new Semaphore(2);
		
		Person p1 = new Person(semaphore, "A");
		
		p1.start();
		
		Person p2 = new Person(semaphore, "B");
		
		p2.start();
		
		Person p3 = new Person(semaphore, "C");
		
		p3.start();
		
		
		
	}
}
class Person extends Thread
{
	private Semaphore semaphore;
	
	public Person(Semaphore semaphore, String name)
	{
		this.semaphore = semaphore;
		setName(name);
	}
	@Override
	public void run()
	{
		System.out.println(getName()+" waiting...");
		try
		{
			this.semaphore.acquire();
			System.out.println(getName()+" serveice...");
			Thread.sleep(1000);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		this.semaphore.release();
		System.out.println(getName()+" over...");
	}
	
}