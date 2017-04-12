package exercise1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Interceptor {

	int clientPort;
	int serverPort;
	InetAddress serverIp;
	String pathname;
	
	protected Socket sockServer;
	protected Socket sockClient;
	protected ServerSocket server;
	protected InputStream inServer;
	protected OutputStream outServer;
	protected InputStream inClient;
	protected OutputStream outClient;
	
	public static void main(String[] args) {
		
		
		if (args.length < 4) {
			System.out.println("Sintaxis de llamada: java EcoCliente <host> <puerto cliente> <puerto servidor> <path fichero destino>");
			System.exit(-1);
		}
		
		try {
			Interceptor interceptor = new Interceptor(Integer.valueOf(args[1]).intValue(),
					Integer.valueOf(args[2]).intValue(),
					InetAddress.getByName(args[0]),
					args[3]);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Interceptor (int clientPort, int serverPort, InetAddress ip, String pathname) {
		Scanner keyboard = new Scanner(System.in);
		
		this.clientPort = clientPort;
		this.serverPort = serverPort;
		this.serverIp = ip;
		this.pathname = pathname;
		
		try {
			sockServer = new Socket(ip, serverPort);
			inServer = sockServer.getInputStream();
			outServer = sockServer.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			server = new ServerSocket(clientPort);
			sockClient = server.accept();
			
			inClient = sockClient.getInputStream();
			outClient = sockClient.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File fileClient = new File(pathname + "/client.txt");
		PrintWriter pwClient = null;
		try {
			pwClient = new PrintWriter(fileClient);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		File fileServer = new File(pathname + "/server.txt");
		PrintWriter pwServer = null;
		try {
			pwServer = new PrintWriter(fileServer);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Worker clientWorker = new Worker("Client connection worker", inClient, outServer, pwClient);
		Worker serverWorker = new Worker("Server connection worker", inServer, outClient, pwServer);
		
		clientWorker.start();
		serverWorker.start();
		
		System.out.println("Push ENTER to skip");
		keyboard.nextLine();

		clientWorker.stopWorking();
		try {
			clientWorker.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serverWorker.stopWorking();
		try {
			serverWorker.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			sockClient.close();
			sockServer.close();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
