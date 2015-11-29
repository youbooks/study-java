

import java.util.concurrent.Exchanger;
/*
 * 两个线程交换输出信息
 */
public class ExchangerDemo
{
	public static void main(String[] args)
	{
		Exchanger<String> ex = new Exchanger<String>();
		
		new A(ex).start();
		new B(ex).start();
	}
}
class A extends Thread
{
	Exchanger<String> exchanger;
	
	public A(Exchanger<String> exchanger)
	{
		this.exchanger = exchanger;
	}
	@Override
	public void run()
	{
		try
		{
			String str = this.exchanger.exchange("Hello");
			System.out.println(str);
			str = this.exchanger.exchange("AAAA");
			System.out.println(str);
			str = this.exchanger.exchange("1111");
			System.out.println(str);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
class B extends Thread
{
	private Exchanger<String> exhanger;
	
	public B(Exchanger<String> ex)
	{
		this.exhanger = ex;
	}
	@Override
	public void run()
	{
		try
		{
			String str = this.exhanger.exchange("World");
			System.out.println(str);
			str = this.exhanger.exchange("BBBB");
			System.out.println(str);
			str = this.exhanger.exchange("0000000");
			System.out.println(str);
					
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
}
