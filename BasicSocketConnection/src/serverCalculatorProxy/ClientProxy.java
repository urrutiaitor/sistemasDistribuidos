package serverCalculatorProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProxy {

	
	private Socket sock;
	private PrintWriter out;
	private BufferedReader in;

	public ClientProxy (String args, int port) {
		try {
			sock = new Socket(args, port);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(int i, int num1, int num2) {
		String str = null;
		
		switch (i) {
		case 0:
			str = "$gcd " + num1 + " " + num2 + "$";
			break;

		default:
			break;
		}
		
		out.println(str);
			
	}
	
	public void write(int i, int num1) {
		String str = null;
		
		switch (i) {
		case 1:
			str = "$factorial " + num1 + "$";
			break;

		default:
			break;
		}
		
		out.println(str);
	}

	public int read() {
		try {
			return in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public void close() {
		try {
			out.close();
			in.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
