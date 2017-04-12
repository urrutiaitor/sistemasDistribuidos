package TCPserverCalculatorMT;

/*----------------------------------------------------------------------
 Al invocarlo deber� especificar en la l�nea de comandos la direcci�n IP
y el puerto del host al que deseas conectarte. P.e.:

  java EcoCliente 172.17.121.177 8888
-----------------------------------------------------------------------*/
import java.io.*;
import java.net.*;

class CalcClient {
	public static void main(String[] args) throws IOException {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		int opcion, n1, n2, nResult, port;
		String str;
		ClientProxy proxy;
		InetAddress dirIP;
		boolean inicializado = false;

		if (args.length < 2) {
			System.out.println("Callong sintax: java CalcClient <host> <port>");
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
					System.out.print("first operand: ");
					str = stdIn.readLine();
					n1 = Integer.valueOf(str).intValue();
					System.out.print("second operand: ");
					str = stdIn.readLine();
					n2 = Integer.valueOf(str).intValue();
					nResult = proxy.gcd(n1, n2);
					System.out.println("gcd(" + n1 + "," + n2 + ")=" + nResult);
					break;
				case 2:
					System.out.print("the operand: ");
					str = stdIn.readLine();
					n1 = Integer.valueOf(str).intValue();
					nResult = proxy.factorial(n1);
					System.out.println(n1 + "!=" + nResult);
					break;
				}
			}
		} catch (Exception e) {
			if (inicializado)
				System.out.println("Server falut or communication lost");
			else
				System.out.println("invalid address or server not running");
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