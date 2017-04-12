package exercise1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Worker extends Thread {

	protected InputStream in;
	protected OutputStream out;
	String name;
	PrintWriter pw;
	boolean working = true;
	
	public Worker (String name, InputStream in, OutputStream out) {
		this.name = name;
		this.in = in;
		this.out = out;
	}
	
	public Worker(String name, InputStream in, OutputStream out, PrintWriter pwServer) {
		this.name = name;
		this.in = in;
		this.out = out;
		this.pw = pwServer;
	}

	public void run () {
		byte b = 0;
		
		while (working) {
		
			try {
				b = (byte) in.read();
				out.write(b);
				pw.write(b);
				pw.flush();
				out.flush();
			} catch (IOException e) {
				System.err.println("The " + name + " connection has been stopped");
				System.err.println("Closing " + name + " system");
				return;
			}
			
			byte[] bytes = new byte[1];
			bytes[0] = b;
			String s = new String(bytes);
			
			System.out.println(name + " transmits byte " + s);
			
		}
		
		System.out.println(name + " stops working");
	}

	public void stopWorking() {
		working = false;
	}
}
