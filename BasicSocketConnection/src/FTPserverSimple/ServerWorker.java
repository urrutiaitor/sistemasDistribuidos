package FTPserverSimple;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerWorker implements Runnable {

	boolean working = true;
	ServerSocket s;
    Socket c;
	
    public ServerWorker(ServerSocket s) {
		this.s = s;
	}
    
	@Override
	public void run() {
		System.out.println("Server worker started");
		
		while (working) {
	    	try {
				c = s.accept();
				System.out.println("Connection accepted");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	ServerConnWorker wr = new ServerConnWorker(c);
			new Thread(wr).start();
	    }
	}

	public void close () {
		working = false;
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
