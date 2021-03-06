package UDPserverCalculatorSimple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientProxy {

	DatagramSocket clientSocket;
	InetAddress address;
	int port;
	
	public ClientProxy (String host, int port) {
		this.port = port;
		
		try {
			clientSocket = new DatagramSocket();
			address = InetAddress.getByName(host);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(int type, int user, int[] data) {
		String str = null;
		
		switch (type) {
		case 0:
			str = "$INIT" + "%" + user + "$";
			break;
		case 1:
			str = "$FINAL" + "%" + user + "$";
			break;
		case 2:
			str = "$" + user + "%" + "GCD" + "%" + data[0] + "%" + data[1] + "$";
			break;
		case 3:
			str = "$" + user + "%" + "FACTORIAL" + "%" + data[0] + "$";
			break;
		default:
			break;
		}
		
		DatagramPacket p = new DatagramPacket(str.getBytes(), str.length(), address, port);
		
		try {
			clientSocket.send(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	public int read() {
		byte[] buf = new byte[256];
		DatagramPacket p = new DatagramPacket(buf, 256);
		
		try {
			clientSocket.receive(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] data = p.getData();
		
		int initIndex = 0;
		int finalIndex = 0;
		
		while (data[initIndex] != '$') initIndex++;
		
		finalIndex = initIndex;
		
		while (data[finalIndex] != '$') finalIndex++;
		
		String pureMessage = new String(data, initIndex, finalIndex - initIndex);
		
		String[] comp = pureMessage.split("%");
		
		return Integer.valueOf(comp[2]);
		
	}
}
