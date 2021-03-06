package UDPserverCalculatorSimple;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
	
	/* PROTOCOL:
	 * Message init: '$'
	 * Message final: '$'
	 * Message components:
	 * -> User id
	 * -> Messege type
	 * -> Content
	 * Messege component divisor: '%'
	 * Messege types:
	 * -> User messege types:
	 * ----> Init messege: Type INIT
	 * ----> Final messege: Type FINAL
	 * ----> Calculus request: Type CALCULUS
	 * -> Server messege types:
	 * ----> Init response: Type INIT
	 * ----> Calculus response: Type CALCULUS
	 * ----> Final request: Type FINAL
	 */
	
	
	public static void main(String args[]) throws IOException {
		int port;

		if (args.length < 1) {
			System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[0]).intValue();

		DatagramSocket serverSocket = new DatagramSocket(port);
		ServerWorker worker = new ServerWorker(serverSocket);
		new Thread(worker).start();

		System.out.println("Waiting for exit");
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
		System.out.println("Exit");
		worker.close();
		serverSocket.close();

	}
}