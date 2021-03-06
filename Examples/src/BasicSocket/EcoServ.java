package BasicSocket;

/*----------------------------------------------------------------------
  Programa que esperando por un socket tramas finalizadas por el salto
de l�nea responde con la misma l�nea convertida a may�sculas. Se puede
comprobar junto con  EcoCliente.java

 Al invocarlo deber� especificar en la l�nea de comandos el puerto por
el que vas a atender peticiones. P.e.:

  java EcoServ 8888
-----------------------------------------------------------------------*/
import java.io.*;
import java.net.*;

public class EcoServ {
	public static void main(String args[]) throws IOException {
		BufferedReader in;
		OutputStreamWriter out;
		String strIn, strOut;
		int port;

		ServerSocket s;
		Socket c;

		if (args.length < 1) {
			System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[0]).intValue();
		s = new ServerSocket(port);
		c = s.accept();
		in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		out = new OutputStreamWriter(c.getOutputStream());
		while ((strIn = in.readLine()) != null) {
			System.out.println("recib� " + strIn);
			strOut = strIn.toUpperCase();
			out.write(strOut + '\n');
			out.flush();
			System.out.println("\trespond�: " + strOut);
		}
		;
		out.close();
		in.close();
		c.close();
		s.close();
	}
}