package UDPserverCalculatorSimple;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) throws IOException {
		int port;
		int option = 0;
		int type = 0;
		int user = 0;
		int[] data = new int[10];

		Scanner keyboard = new Scanner(System.in);

		if (args.length < 2) {
			System.out.println("Sintaxis de llamada: java EcoCliente <host> <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[1]).intValue();

		ClientProxy proxy = new ClientProxy(args[0], port);
		proxy.write(type, user, data);
		
		do {
			System.out.println("Choose option:");
			System.out.println("1.- GCD of 2 numbers");
			System.out.println("2.- Factorial of a number");
			System.out.println("3.- End connection");
			option = keyboard.nextInt();
			keyboard.nextLine();

			switch (option) {
			case 1:
				type = 2;
				System.out.print("First number: ");
				data[0] = keyboard.nextInt();
				keyboard.nextLine();
				System.out.print("Second number: ");
				data[1] = keyboard.nextInt();
				keyboard.nextLine();

				System.out.println("Answer of GCD: " + proxy.read());
				break;
			case 2:
				type = 3;
				System.out.print("Number: ");
				data[0] = keyboard.nextInt();
				keyboard.nextLine();

				System.out.println("Answer of factorial: " + proxy.read());
				break;
			case 0:
				type = 3;
				break;
			}
			
			proxy.write(type, user, data);

		} while (option != 0);

	}
}