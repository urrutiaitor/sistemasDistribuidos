package TCPsimpleCalculatorST;

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
import java.net.*;

public class CalculiServer {
	public static void main(String args[]) throws IOException {

		int port, numClients = 0;

		ServerSocket s;
		Socket c;

		if (args.length < 1) {
			System.out.println("Sintaxis de llamada: java ServCalculo <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[0]).intValue();
		s = new ServerSocket(port);
		while (numClients++ < 2) {
			c = s.accept();
			System.out.println("un clienteNuevo");
			attendClient(c);
			System.out.println("el cliente se desconect�");
		}
		s.close();
	}

	protected static void attendClient(Socket sock) {
		int operation, result;
		int[] operandos = new int[2];
		ServerProxy sPrx;
		CalculiServant servant;

		try {
			sPrx = new ServerProxy(sock);
			servant = new CalculiServant();
			while ((operation = sPrx.waitCommand(operandos)) != ServerProxy.connectionClosed) {
				switch (operation) {
				case 2:
					result = servant.gcd(operandos[0], operandos[1]);
					sPrx.answer(result);
					break;
				case 1:
					result = servant.factorial(operandos[0]);
					sPrx.answer(result);
					break;
				default:
					sPrx.answerNull();
				}
			}
			;
			sock.close();
		} catch (IOException e) {
		}
	}
}