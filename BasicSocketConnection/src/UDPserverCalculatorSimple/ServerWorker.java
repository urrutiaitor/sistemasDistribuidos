package UDPserverCalculatorSimple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerWorker implements Runnable {

	boolean working = true;
	DatagramSocket s;
    final static int bufferLength = 256;
    int userIndex = 0;
	
    public ServerWorker(DatagramSocket s) {
		this.s = s;
	}
    
	@Override
	public void run() {
		System.out.println("Server worker started");
		
		while (working) {
			try {
	    		byte[] buf = new byte[bufferLength];
	    		DatagramPacket rPacket = new DatagramPacket(buf, bufferLength);
	    		s.receive(rPacket);
	    		ServerConnWorker wr = new ServerConnWorker(rPacket.getAddress(), rPacket.getPort(), rPacket.getData(), userIndex);
				new Thread(wr).start();
				userIndex++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
