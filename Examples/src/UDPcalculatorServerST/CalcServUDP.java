package UDPcalculatorServerST;

/*----------------------------------------------------------------------
  Programa que esperando la llegada de datagramas que contengan un string
responden con un datagrama conteniendo el mismo string convertido a
may�scula. El programa finaliza cuando recibe la palabra "quit" y 
responde con "QUIT". Se puede comprobar junto con EcoClientMens.java
-----------------------------------------------------------------------*/
import java.io.*;
import java.net.*;

public class CalcServUDP {
	public static void main(String args[]) throws IOException {
		int port, operation, result, operands[] = new int[2];
		DatagramSocket s;
		byte[] buf = new byte[256];
		DatagramPacket pIn;
		MsgContent msgContent;

		if (args.length < 1) {
			System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[0]).intValue();
		s = new DatagramSocket(port);
		do {
			pIn = new DatagramPacket(buf, buf.length);
			s.receive(pIn);
			msgContent = new MsgContent(pIn.getData(), pIn.getLength());
			operation = msgContent.extractOperation(operands);
			switch (operation) {
			case 2:
				result = gcd(operands[0], operands[1]);
				msgContent = MsgContent.createMsgForResult(result);
				break;
			case 1:
				result = factorial(operands[0]);
				msgContent = MsgContent.createMsgForResult(result);
				break;
			}
			s.send(msgContent.putInDatagram(pIn.getAddress(), pIn.getPort()));
		} while (true);
		// s.close();
	}

	static int gcd(int n, int m) {
		int x;
		while (n % m != 0) {
			x = n % m;
			n = m;
			m = x;
		}
		return m;
	}

	static int factorial(int n) {
		int i, f = 1;

		for (i = 2; i <= n; i++)
			f *= i;
		return f;
	}
}