import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * ExecutorCompletionService:
 * include:   |-Executor
 *            |-BlockingQueue<Future<V>>
 * @author lzyer
 * @date 2015/12/26
 *
 */

public class ExecutorCompletionServiceTest
{
	
	public static void main(String[] args)
	{
		final List<String> topSite = Arrays.asList("www.baidu.com",
				"qq.com","360.cn","sougou.com","weibo.com",
				"www.csdn.net","sina.com.cn","ifeng.com",
				"taobao.com","cctv.cn","youku.com");
		final ExecutorService  pool = Executors.newFixedThreadPool(5);
		
		final ExecutorCompletionService<String> service = new ExecutorCompletionService<String>(pool);
		
		for(final String str: topSite)
		{
			service.submit(new Callable<String>()
			{
				
				@Override
				public String call() throws Exception
				{
					HttpURLConnection conn = (HttpURLConnection)(new URL("http://"+str).openConnection());
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					
					return reader.readLine();
				}
			});
		}
		for(int i=0; i<topSite.size(); i++)
		{
			try
			{
				final Future<String> future = service.take();
				String result = future.get();
				System.out.println(result);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		//要关闭池
		pool.shutdown();
	}
}
