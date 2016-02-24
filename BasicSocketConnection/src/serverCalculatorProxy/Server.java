package serverCalculatorProxy;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server
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
    s = new ServerSocket(port);
    
    ServerWorker worker = new ServerWorker(s);
    new Thread(worker).start();
    
    System.out.println("Waiting for exit");
    Scanner keyboard = new Scanner(System.in);
    keyboard.nextLine();
    System.out.println("Exit");
    worker.close();
    s.close();
    
  }
}