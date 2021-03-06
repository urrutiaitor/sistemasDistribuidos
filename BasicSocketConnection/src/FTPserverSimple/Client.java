package FTPserverSimple;

import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		int port;

		Scanner keyboard = new Scanner(System.in);

		if (args.length < 2) {
			System.out.println("Sintaxis de llamada: java EcoCliente <host> <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[1]).intValue();

		ClientProxy proxy = new ClientProxy(args[0], port);

		System.out.println("Source file name: ");
		String sourcePath = keyboard.nextLine();
		
		System.out.println("New path name: ");
		String newPath = keyboard.nextLine();
		
		proxy.ask(sourcePath);
		
		File file = new File(newPath);
		proxy.recive(file);
		
		if (file == null) System.err.println("Something has gone wrong");
		else System.out.println("File copyed");

		proxy.close();
		stdIn.close();

	}
}