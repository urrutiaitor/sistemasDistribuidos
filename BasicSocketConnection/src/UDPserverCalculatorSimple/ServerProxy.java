package UDPserverCalculatorSimple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerProxy {
	
	public void read (byte[] message, int[] values, int type) {
		int initIndex = 0;
		int finalIndex = 0;
		
		while (message[initIndex] != '$') initIndex++;
		
		finalIndex = initIndex;
		
		while (message[finalIndex] != '$') finalIndex++;
		
		String pureMessage = new String(message, initIndex, finalIndex - initIndex);

		System.out.println("Server proxy read message: " + pureMessage);
		
		String[] comp = pureMessage.split("%");
		
		if (comp.length < 2) return;
		
		switch (comp[1]) {
		case "GCD":
			type = 2;
			values[0] = Integer.valueOf(comp[2]);
			values[1] = Integer.valueOf(comp[3]);
			break;
		case "FACTORIAL":
			type = 3;
			values[0] = Integer.valueOf(comp[2]);
			break;

		default:
			System.err.println("Connection closed");
			return;
		}
	}
	
	public byte[] write (int userId, int type, int answer, String message) {
		String typeString = null;
		
		switch (type) {
		case 0: /* INIT */
			typeString = "INIT";
			break;
		case 1: /* FINAL */
			typeString = "FINAL";
			break;
		case 2:
			typeString = "GCD";
			break;
		case 3:
			typeString = "FACTORIAL";
			break;

		default:
			break;
		}

		if (type == 0) message = "$" + userId + "%" + typeString + "$";
		if (type == 1) message = "$" + userId + "%" + typeString + "$";
		if (type > 1) message = "$" + userId + "%" + typeString + "%" + answer + "$";
		
		return message.getBytes();
	}

}
