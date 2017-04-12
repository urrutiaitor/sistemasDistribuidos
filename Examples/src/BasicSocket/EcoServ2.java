package BasicSocket;

/*----------------------------------------------------------------------
 Programa que esperando por un socket tramas finalizadas por el salto
de l�nea responde con la misma l�nea convertida a may�sculas. Pero ha de
poder atender a varios clientes uno detras de otro (no  en paralelo).
Haz que finalice cuando haya atendido a tres.

 Se puede comprobar junto con varias ejecuciones de EcoCliente.java
 
  Al invocarlo deber� especificar en la l�nea de comandos el puerto por
el que vas a atender peticiones. P.e.:

  java EcoServ 8888
-----------------------------------------------------------------------*/
import java.io.*;
import java.net.*;

public class EcoServ2
{
  private final static int maxClientes=3;
  public static void main(String args[]) throws IOException
  {
    int port,nClientes=0;
    ServerSocket s;
    Socket c;

    if(args.length<1)
    {
      System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
      System.exit(-1);
    }
    port=Integer.valueOf(args[0]).intValue();
    s= new ServerSocket(port);
    while(nClientes<maxClientes)
    {
      c = s.accept();    
      servirCliente(c);
      nClientes++;
    }
    s.close();
  }
  static void servirCliente(Socket sock) throws IOException
  {
    String strIn,strOut,strIPCliente;
    BufferedReader in;
    OutputStreamWriter out;
    
    strIPCliente=sock.getInetAddress().getHostAddress();
    in=new BufferedReader(new InputStreamReader(sock.getInputStream()));
    out=new OutputStreamWriter(sock.getOutputStream());
    while((strIn=in.readLine())!=null)
    {
      System.out.println("recib� de "+strIPCliente+" : "+strIn);
      strOut=strIn.toUpperCase();
      out.write(strOut+'\n');
      out.flush();
      System.out.println("\trespond�: "+strOut);
    };
    out.close();
    in.close();
    sock.close();
  }
}