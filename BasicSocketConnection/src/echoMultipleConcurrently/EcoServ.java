package echoMultipleConcurrently;

import java.io.*;
import java.net.*;

public class EcoServ
{
  public static void main(String args[]) throws IOException
  {
    int port;

    ServerSocket s;
    Socket c;

    if(args.length<1)
    {
      System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
      System.exit(-1);
    }
    port=Integer.valueOf(args[0]).intValue();
    s= new ServerSocket(port);
    
    while (true) {
    	c = s.accept();
    	WorkerRunnable wr = new WorkerRunnable(c);
		new Thread(wr).start();
    }
  }
}