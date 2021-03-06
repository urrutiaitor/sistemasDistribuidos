package serverCalculatorProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerProxy {

	Socket clientSocket;
	InputStream in;
	OutputStream out;
	
	public ServerProxy(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			in = clientSocket.getInputStream();
			out  = clientSocket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void read (int operation, int values[]) {
		char c;
		String strIn = "";
		
		try {
			while ((c = (char) in.read()) != '$') {
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while ((c = (char) in.read()) != '$') {
				if (strIn == "") strIn = String.valueOf(c);
				else strIn += String.valueOf(c);
				System.out.println("Char read: " + c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Input: " + strIn);
		String[] comp = strIn.split(" ");
		
		switch (comp[0]) {
		case "gcd":
			operation = 0;
			values[0] = Integer.valueOf(comp[1]);
			values[1] = Integer.valueOf(comp[2]);
			break;
		case "factorial":
			operation = 1;
			values[0] = Integer.valueOf(comp[1]);
			break;

		default:
			System.err.println("Connection closed");
			return;
		}
		
		strIn = "";
	}
	
	public void write (int answer) {
		try {
			out.write(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Response: " + answer);
	}

}
