package echoMultipleConcurrently;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerRunnable implements Runnable {

	Socket sock = null;

	public WorkerRunnable(Socket clientSocket) {
		this.sock = clientSocket;
	}

	@Override
	public void run() {
		
		try {
			PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			System.out.println("Socket connection established");
			String strIn;
			
			while((strIn=in.readLine())!=null)
		    {
		      System.out.println("recibo "+strIn);
		      String strOut = strIn.toUpperCase();
		      out.write(strOut+'\n');
		      out.flush();
		      System.out.println("\trespondo: "+strOut);
		    };
		    out.close();
		    in.close();
		    sock.close();
			
		} catch (IOException e) {
			System.err.println("Connection closed");
		}
	}

}
