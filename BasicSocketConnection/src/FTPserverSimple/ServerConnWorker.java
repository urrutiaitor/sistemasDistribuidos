package FTPserverSimple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerConnWorker implements Runnable {

	Socket sock = null;
	ServerProxy proxy;

	public ServerConnWorker(Socket clientSocket) {
		this.sock = clientSocket;
		proxy = new ServerProxy(clientSocket);
		System.out.println("Connection established");
	}

	@Override
	public void run() {
		String path = proxy.ask();
		
		File file = new File(path);
		FileReader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long fileLength = file.length();
		int b = 0;
		
		int packetSize = 28;
		long numPacket = fileLength/packetSize;
		
		System.out.println("Starting to send file");
		for (long i = 0; i < numPacket; i++) {
			System.out.println("Sending packet " + i + "/" + numPacket);
			byte[] bytes = new byte[packetSize + 4];
			bytes[0] = '$';
			bytes[1] = (byte) (numPacket - i);
			bytes[2] = '%';
			
			for (int a = 3; a < packetSize + 4; a++) {
				try {
					b = reader.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (b == -1) {
					bytes[a] = '$';
					String line = new String(bytes, 0, a + 1);
					System.out.println("Sending last packet: " + line);
					proxy.send(line);
				} else {
					bytes[a] = (byte) b;
				}
			}
			
			String line = new String(bytes, 0, packetSize + 4);
			System.out.println("Sending packet: " + line);
			proxy.send(line);
		}
		
	}

}
