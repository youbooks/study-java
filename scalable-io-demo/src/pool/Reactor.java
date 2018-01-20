package pool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
* @ClassName: Reactor
* @Description: 根据 Scalable IO in Java写的实例。
* @author Lzyer 
* @date 2017年3月7日
 */
public class Reactor implements Runnable
{
	final Selector selector;
	final ServerSocketChannel serverSocketChannel;
	public Reactor(int port) throws IOException
	{
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		key.attach(new Acceptor());
	}
	public void run()
	{
		try{
			while(!Thread.interrupted()){
				int n = selector.select();
				if(n == 0)
					continue;
				Set<SelectionKey> set = selector.selectedKeys();
				Iterator<SelectionKey> iterator = set.iterator();
				while(iterator.hasNext()){
					dispatch(iterator.next());
				}
				set.clear();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void dispatch(SelectionKey k)
	{
		Runnable runnable = (Runnable) k.attachment();
		if(runnable != null)
			runnable.run();
	}
	class Acceptor implements Runnable
	{
		public void run()
		{
			try
			{
				SocketChannel c = serverSocketChannel.accept();
				if(c != null){
					System.out.println("New Connection ...");
					new Handler3(selector, c);
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	* @ClassName: Handler3
	* @Description: 使用线程池
	* @author Lzyer 
	* @date 2017年3月8日
	*
	 */
	class Handler3 implements Runnable
	{
		final SocketChannel socket;
		final SelectionKey key;
		ByteBuffer input = ByteBuffer.allocate(1024);
		ByteBuffer output = ByteBuffer.allocate(1024);
		static final int READING = 0,SENDING =1, PROCESSING =3;
		int state = READING;
		ExecutorService pool = Executors.newCachedThreadPool();
		public Handler3(Selector selector, SocketChannel c) throws IOException
		{
			this.socket = c;
			this.socket.configureBlocking(false);
			key = socket.register(selector, SelectionKey.OP_READ);
			key.attach(this);
			selector.wakeup();
		}
		public void run()
		{
			try
			{
				if(state == READING)
					read();
				else if(state == SENDING)
					send();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		synchronized void read() throws IOException { 
			socket.read(input);
			if (inputIsComplete()) {
				state = PROCESSING;
				pool.execute(new Processer());
			}
		}
		void send() throws IOException {
			output.flip();
			socket.write(output);
			if(outputIsComplete()){
				key.cancel();
			}
			state = READING;
			key.interestOps(SelectionKey.OP_READ);
		}
		void process(){
			//读数据
			StringBuilder reader = new StringBuilder();
			input.flip();
			while(input.hasRemaining()){
				reader.append((char)input.get());
			}
			System.out.println("[Client-INFO]");
			System.out.println(reader.toString());
			String str = "HTTP/1.1 200 OK\r\nDate: Fir, 10 March 2017 21:20:01 GMT\r\n"+
					"Content-Type: text/html;charset=UTF-8\r\nContent-Length: 32\r\nConnection:close"+
					"\r\n\r\nWelcome JAVA World "+System.currentTimeMillis();
			output.put(str.getBytes());
			System.out.println("process .... ");
		}
		boolean inputIsComplete(){
			return input.hasRemaining();
		}
		boolean outputIsComplete(){
			return output.hasRemaining();
		}
		synchronized void processAndHandOff() {
			process();
			state = SENDING; // or rebind attachment
			key.interestOps(SelectionKey.OP_WRITE);
			System.out.println("processer over....");
		}
		class Processer implements Runnable
		{
			public void run()
			{
				processAndHandOff();
			}
		}
	}
	public static void main(String[] args) throws IOException
	{
		new Thread(new Reactor(9001)).start();
		System.out.println("Server start...");
	}
}
