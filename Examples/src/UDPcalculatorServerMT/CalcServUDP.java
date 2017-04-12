package UDPcalculatorServerMT;

import java.io.*;

public class CalcServUDP {
	public static void main(String args[]) throws IOException {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		int port;
		WorkerManager jw;

		if (args.length < 1) {
			System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[0]).intValue();
		jw = new WorkerManager(port);
		jw.start();
		System.out.println("--------- Pulse \"Return\" para finalizar  ----");
		stdIn.readLine();
		jw.finish();
		try {
			jw.join();
		} catch (InterruptedException e) {
		}
	}

}