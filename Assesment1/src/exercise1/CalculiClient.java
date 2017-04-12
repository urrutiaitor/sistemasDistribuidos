package exercise1;

/*----------------------------------------------------------------------
 Al invocarlo deber� especificar en la l�nea de comandos la direcci�n IP
y el puerto del host al que deseas conectarte. P.e.:

  java EcoCliente 172.17.121.177 8888
-----------------------------------------------------------------------*/
import java.io.*;
import java.net.*;

class CalculiClient {
	public static void main(String[] args) throws IOException {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		int opcion, n1, n2, nResult, port;
		String str;
		ClientProxy proxy;
		InetAddress dirIP;
		boolean inicializado = false;

		if (args.length < 2) {
			System.out.println("Sintaxis de llamada: java EcoCliente <host> <puerto>");
			System.exit(-1);
		}
		try {
			port = Integer.valueOf(args[1]).intValue();
			dirIP = InetAddress.getByName(args[0]);
			proxy = new ClientProxy(dirIP, port);
			inicializado = true;
			while ((opcion = menu()) != 0) {
				switch (opcion) {
				case 1:
					System.out.print("primer n�mero: ");
					str = stdIn.readLine();
					n1 = Integer.valueOf(str).intValue();
					System.out.print("segundo n�mero: ");
					str = stdIn.readLine();
					n2 = Integer.valueOf(str).intValue();
					nResult = proxy.gcd(n1, n2);
					System.out.println("mcd(" + n1 + "," + n2 + ")=" + nResult);
					break;
				case 2:
					System.out.print("de qu� n�mero: ");
					str = stdIn.readLine();
					n1 = Integer.valueOf(str).intValue();
					nResult = proxy.factorial(n1);
					System.out.println(n1 + "!=" + nResult);
					break;
				}
			}
		} catch (Exception e) {
			if (inicializado)
				System.out.println("Servido ca�do o fallan las comunicaciones");
			else
				System.out.println("direcci�n errones o servidor no disponible");
		}
	}

	public static int menu() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str;
		int n;

		System.out.println("Teclea:");
		System.out.println("\t1: para calcular mcd de dos n�meros");
		System.out.println("\t2: para calcular el factorial� de un n�mero");
		System.out.println("\t0: para finalizar");
		str = in.readLine();
		n = Integer.valueOf(str).intValue();
		return n;
	}
}