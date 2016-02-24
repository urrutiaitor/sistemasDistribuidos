package serverCalculatorProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerConnWorker implements Runnable {

	Socket sock = null;
	ServerProxy proxy;
	Calculator calculator;

	public ServerConnWorker(Socket clientSocket) {
		this.sock = clientSocket;
		proxy = new ServerProxy(clientSocket);
		calculator = new Calculator();
		System.out.println("Connection established");
	}

	@Override
	public void run() {
		do {
			int ans = 0;
			int operation = 0;
			int[] values = new int[2];
			proxy.read(operation, values);
			
			switch (operation) {
			case 0:
				ans = calculator.gcd(values[0], values[1]);
				break;
			case 1:
				ans = calculator.factorial(values[0]);
				break;

			default:
				break;
			}
			
			proxy.write(ans);
		} while (true);
	}

}
