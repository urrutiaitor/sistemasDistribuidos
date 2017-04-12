package BasicSocket;

/*----------------------------------------------------------------------
 Programa que esperando por un socket tramas finalizadas por el salto
de l�nea responde con la misma l�nea convertida a may�sculas. Pero ha de
poder atender a varios clientes en paralelo creando para ello los threads
necesarios. Haz que finalice cuando haya atendido a tres.

 Se puede comprobar junto con varias ejecuciones de EcoCliente.java
 
  Al invocarlo deber� especificar en la l�nea de comandos el puerto por
el que vas a atender peticiones. P.e.:

  java EcoServ 8888
-----------------------------------------------------------------------*/
import java.io.*;
import java.net.*;

public class EcoServ3
{
  private final static int maxClients=3;
  public static void main(String args[]) throws IOException
  {
    int port,nClientes=0,i;
    ServerSocket s;
    Socket c;
    Operator operatorPool[]=new Operator[maxClients];

    if(args.length<1)
    {
      System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
      System.exit(-1);
    }
    port=Integer.valueOf(args[0]).intValue();
    s= new ServerSocket(port);
    while(nClientes<maxClients)
    {
      c = s.accept();    
      operatorPool[nClientes]=new Operator(c);
      operatorPool[nClientes].start();
      nClientes++;
    }
    try
    {
      for(i=0;i<maxClients;i++) operatorPool[i].join();
    }
    catch(InterruptedException e) {}
    s.close();
  }
}