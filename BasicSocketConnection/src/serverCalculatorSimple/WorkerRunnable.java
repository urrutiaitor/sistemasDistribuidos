package serverCalculatorSimple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerRunnable implements Runnable {

	Socket sock = null;

	public WorkerRunnable(Socket clientSocket) {
		this.sock = clientSocket;
	}

	@Override
	public void run() {

		try {
			
			InputStream in = sock.getInputStream();
			OutputStream out  = sock.getOutputStream();
			

			System.out.println("Socket connection established");
			String strIn = "";
			int ans = 0;
			char c = 0;

			do {
				while ((c = (char) in.read()) != '$') {
					//strIn = strIn.concat(String.valueOf(c));
					if (strIn == "") strIn = String.valueOf(c);
					else strIn += String.valueOf(c);
					System.out.println("Char read: " + c);
					System.out.println("\t\t\tMomentum input: " + strIn);
				}
				System.out.println("Char read: " + c);
				
				System.out.println("Input: " + strIn);
				String[] comp = strIn.split(" ");
				
				switch (comp[0]) {
				case "gcd":
					ans = gcd(Integer.valueOf(comp[1]), Integer.valueOf(comp[2]));
					break;
				case "factorial":
					ans = factorial(Integer.valueOf(comp[1]));
					break;

				default:
					System.err.println("Connection closed");
					return;
				}
				
				out.write(ans);
				System.out.println("Response: " + ans);
				
				strIn = "";
			} while (true);

		} catch (IOException e) {
			System.err.println("Connection closed");
		}
	}

	int gcd(int n, int m) {
		int x;

		while (n % m != 0) {
			x = n % m;
			n = m;
			m = x;
		}
		return m;
	}

	int factorial(int n) {
		int i, f = 1;

		for (i = 2; i <= n; i++)
			f *= i;
		return f;
	}

}
