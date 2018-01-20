
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ClientTest {

	private SocketChannel client;

	private Selector selector;

	private boolean running;

	public void init() {
		try {

			this.client = SocketChannel.open();

			this.client.configureBlocking(false);

			this.client.connect(new InetSocketAddress("localhost", 2222));

			this.selector = Selector.open();

			this.client.register(selector, SelectionKey.OP_CONNECT);

			this.running = true;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void work() {
		while (this.running) {
			try {
				while (selector.select() > 0) {
					Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

					while (iter.hasNext()) {
						SelectionKey key = iter.next();

						iter.remove();

						if (!key.isValid()) {
							continue;
						}
						if (key.isConnectable()) {
							SocketChannel channel = (SocketChannel) key.channel();

							channel.configureBlocking(false);

							channel.register(selector, SelectionKey.OP_READ);

							channel.finishConnect();

							System.out.println("-----connection server success----");

						}
						if (key.isReadable()) {
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							SocketChannel channel = (SocketChannel) key.channel();
							int len;
							while ((len = channel.read(buffer)) > 0) {
								System.out.println("from server-->" + new String(buffer.array(), 0, len));
								buffer.clear();
							}

							System.out.print("enter message:");

							BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

							String line = reader.readLine();

							channel.write(ByteBuffer.wrap((line + "\r\n").getBytes()));

						}

					}

				}

			} catch (Exception e) {
				this.running = false;
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ClientTest ct = new ClientTest();
		ct.init();
		ct.work();
	}
}
