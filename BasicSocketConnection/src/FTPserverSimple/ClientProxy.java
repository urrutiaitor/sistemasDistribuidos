package FTPserverSimple;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;

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
	
	public void ask(String path) {
		out.println("$" + path + "$");
	}

	public void recive(File file) {
		boolean end = false;
		String line = null;
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Starting to recive file");
		
		while (!end) {
			System.out.println("Reading line");
			try {
				line = in.readLine();
				if (line == null) {
					file = null;
					System.err.println("Line null read from socket");
					return;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Recived line: " + line);
			byte[] bytes = processLine(line);
			if (bytes != null) {
				if (bytes.length > 1) {
					try {
						fs.write(bytes);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				} else {
					end = true;
				}
			} else {
				System.err.println("Bytes null read from processLine");
			}
		}
	}
	
	private byte[] processLine (String line) {
		if (line.length() < 5) {
			System.err.println("Line with short length in processLine");
			return null;
		}
		
		if ((line.getBytes()[0] == '$') || (line.getBytes()[line.length() - 1] == '$')) return null;
		
		String content = new String(line.getBytes(), 1, line.length() - 2);
		
		String[] components = content.split("%");
		
		if (components.length < 2) return null;
		
		int index = Integer.valueOf(components[0]);
		
		System.out.println("Readed index " + index);
		
		if (index == -1) {
			byte[] bytes = new byte[1];
			bytes[0] = -1;
			return bytes;
		} else {
			return components[1].getBytes();
		}
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
