package serverCalculatorProxy;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) throws IOException {
		Socket sock = null;
		PrintWriter out = null;
		String str;
		BufferedReader in = null;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		int port;
		int option = 0;

		Scanner keyboard = new Scanner(System.in);

		if (args.length < 2) {
			System.out.println("Sintaxis de llamada: java EcoCliente <host> <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[1]).intValue();

		ClientProxy proxy = new ClientProxy(args[0], port);

		do {
			System.out.println("Elegir opción:");
			System.out.println("1.- GCD of 2 numbers");
			System.out.println("2.- Factorial of a number");
			option = keyboard.nextInt();
			keyboard.nextLine();

			switch (option) {
			case 1:
				System.out.print("First number: ");
				int num1 = keyboard.nextInt();
				keyboard.nextLine();
				System.out.print("Second number: ");
				int num2 = keyboard.nextInt();
				keyboard.nextLine();

				proxy.write(0, num1, num2);

				System.out.println("Answer of GCD: " + proxy.read());
				break;
			case 2:
				System.out.print("Number: ");
				int num = keyboard.nextInt();
				keyboard.nextLine();

				proxy.write(1, num);

				System.out.println("Answer of factorial: " + proxy.read());
				break;

			default:
				System.out.println("Ending...");
				out.print('E');
				out.print('$');
				break;
			}

		} while (option != 0);

		proxy.close();
		stdIn.close();

	}
}