package UDPcalculatorServerST;

/*----------------------------------------------------------------------
  Programa que env�a a un servidor todo lo que escribe el usuario de
forma que empaqueta una l�nea por datagrama y espera a que ese servidor
le devuelva un datagrama conteniendo la misma l�nea convertida a 
may�sculas para finalmente escribirlo en pantalla. El programa finaliza
cuando env�a la palabra "quit". Se puede comprobar junto con
EcoServMens.java
-----------------------------------------------------------------------*/
import java.io.*;
import java.net.*;

class CalcClientUDP {
	public static void main(String[] args) throws IOException {
		DatagramSocket dgrmSock;
		InetAddress ipAddress;
		int port;

		if (args.length < 2) {
			System.out.println("Sintaxis de llamada: java EcoServ <host> <puerto>");
			System.exit(-1);
		}
		ipAddress = InetAddress.getByName(args[0]);
		port = Integer.valueOf(args[1]).intValue();
		dgrmSock = new DatagramSocket();
		attendUser(dgrmSock, ipAddress, port);
		dgrmSock.close();
	}

	public static void attendUser(DatagramSocket dgrmSock, InetAddress ipAddress, int port) throws IOException {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String str;
		int opcion, n1, n2, nResult;
		byte[] bufIn = new byte[256];
		DatagramPacket pIn;
		MsgContent msgContent;

		while ((opcion = menu()) != 0) {
			switch (opcion) {
			case 1:
				System.out.print("first operand: ");
				str = stdIn.readLine();
				n1 = Integer.valueOf(str).intValue();
				System.out.print("second operand: ");
				str = stdIn.readLine();
				n2 = Integer.valueOf(str).intValue();
				msgContent = MsgContent.createMsgForGcd(n1, n2);
				dgrmSock.send(msgContent.putInDatagram(ipAddress, port));
				pIn = new DatagramPacket(bufIn, bufIn.length);
				dgrmSock.receive(pIn);
				msgContent = new MsgContent(pIn.getData(), pIn.getLength());
				nResult = msgContent.getResult();
				System.out.println("mcd(" + n1 + "," + n2 + ")=" + nResult);
				break;
			case 2:
				System.out.print("the operand: ");
				str = stdIn.readLine();
				n1 = Integer.valueOf(str).intValue();
				msgContent = MsgContent.createMsgForFactorial(n1);
				dgrmSock.send(msgContent.putInDatagram(ipAddress, port));
				pIn = new DatagramPacket(bufIn, bufIn.length);
				dgrmSock.receive(pIn);
				msgContent = new MsgContent(pIn.getData(), pIn.getLength());
				nResult = msgContent.getResult();
				System.out.println(n1 + "!=" + nResult);
				break;
			}
		}
	}

	public static int menu() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str;
		int n;

		System.out.println("Type:");
		System.out.println("\t1: to know GCD of two numbers");
		System.out.println("\t2: to kow the FACTORIAL of a number");
		System.out.println("\t0: to finish");
		str = in.readLine();
		n = Integer.valueOf(str).intValue();
		return n;
	}
}