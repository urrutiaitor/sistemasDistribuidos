package TCPserverCalculatorMT;

/*----------------------------------------------------------------------
  Programa que esperando por un socket comandos de c�lculo, las interpreta,
ejecuta la orden y devuelve el resultado. Todas las tramas han de estar
finalizadas por el byte 'S' y admite dos comandos: calculo de mcd(a,b) y
factorial(n)

  Las tramas a intercambiar con el cliente tienen el siguiente formato:
  
  invocaci�n mcd: "mcd <num1> <num2>$"
    respuesta "<resultado>$"
    
  invocaci�n factorial: "factorial <num>$"
    respuesta "<resultado>$"

 Al invocarlo deber� especificar en la l�nea de comandos el puerto por
el que vas a atender peticiones. P.e.:

  java ServCalculo 8888
-----------------------------------------------------------------------*/
import java.io.*;

public class CalcServer {
	public static void main(String args[]) throws IOException {
		int port;
		WorkerManager jefeWorkers;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		if (args.length < 1) {
			System.out.println("Sintaxis de llamada: java ServCalculo <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[0]).intValue();
		jefeWorkers = new WorkerManager(port);
		jefeWorkers.start();
		System.out.println("--------- Pulse \"Return\" para finalizar  ----");
		stdIn.readLine();
		jefeWorkers.finish();
		try {
			jefeWorkers.join();
		} catch (InterruptedException e) {
		}

	}

}