package FTPserverSimple;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerProxy {

	Socket clientSocket;
	PrintWriter out;
	BufferedReader in;
	
	public ServerProxy(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String ask () {
		String line = null;
		
		try {
			line = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] bytes = line.getBytes();
		
		if ((bytes[0] != '$') || (bytes[line.length() - 1] != '$')) {
			return null;
		}
		
		return new String(bytes, 1, line.length() - 2);
	}

	public void send(String line) {
		out.write(line);
	}

}
