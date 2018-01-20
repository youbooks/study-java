
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ServerTest {

	private Selector selector;
	
	private boolean running;
	
	private static int count;
	
	public void init()
	{
		try
		{
			ServerSocketChannel server = ServerSocketChannel.open();

			server.configureBlocking(false);
			
			server.bind(new InetSocketAddress(2222));
			
			this.selector = Selector.open();
			
			server.register(selector, SelectionKey.OP_ACCEPT);
			
			this.running = true;
			
			count = 0;
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("server start");
		
	}
	public void work()
	{
		
		while(this.running)
		{
			 try {
				
				while(selector.select()>0)
				{
					Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
					
					while(iter.hasNext())
					{
						SelectionKey key = iter.next();
						
						iter.remove();
						
						if(!key.isValid())
						{
							continue;
						}
						
						if(key.isAcceptable())
						{
							SocketChannel channel = ((ServerSocketChannel)(key.channel())).accept();
							
							channel.configureBlocking(false);
							
							channel.register(selector, SelectionKey.OP_READ);
							
							System.out.println("---new client connect---");
							count++;
							System.out.println("---total:"+count+"---");
							channel.write(ByteBuffer.wrap("Hello Client\r\n".getBytes()));
						}
						
						if(key.isReadable())
						{
							SocketChannel channel = (SocketChannel)key.channel();
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							int len;
							while((len = channel.read(buffer))>0)
							{
								System.out.println("from client-->"+new String(buffer.array(), 0, len));;
								buffer.clear();
							}
							
							System.out.print("input message: ");
							
							BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
							
							String line = reader.readLine();
							
							channel.write(ByteBuffer.wrap((line+"\r\n").getBytes()));
							
						}
						
					}
				}
			
			} catch (IOException e) {
				this.running = false;
				e.printStackTrace();
			}

		}
		
	}
	public static void main(String[] args) {
		
		ServerTest st = new ServerTest();
		st.init();
		st.work();
		
	}
	
}
