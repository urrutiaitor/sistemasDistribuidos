package UDPserverCalculatorSimple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ServerConnWorker implements Runnable {

	ServerProxy proxy;
	Calculator calculator;
	private InetAddress address;
	private int port;
	private byte[] data;
	int user;
	int type;

	public ServerConnWorker(InetAddress address, int port, byte[] data, int user) {
		this.address = address;
		this.port = port;
		this.data = data;
		proxy = new ServerProxy();
		calculator = new Calculator();
	}

	@Override
	public void run() {
		int ans = 0;
		int operation = 0;
		int[] values = new int[2];
		String message = new String();
		
		proxy.read(data, values, type);
		if (values == null)
			try {
				finalize();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		switch (operation) {
		case 0: /* INIT */
			
			break;
		case 1: /* FINAL */
			
			break;
		case 2:
			ans = Calculator.gcd(values[0], values[1]);
			break;
		case 3:
			ans = Calculator.factorial(values[0]);
			break;

		default:
			break;
		}

		byte[] buf = proxy.write(user, type, ans, message);
		
		DatagramPacket p = new DatagramPacket(buf, buf.length, address, port);
		
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			clientSocket.send(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
