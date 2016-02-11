package echoSimple;

import java.io.*;
import java.net.*;

class EcoCliente
{
  public static void main(String[] args) throws IOException 
  {
    Socket sock = null;
    PrintWriter out = null;
    String str;
    BufferedReader in=null;
    BufferedReader stdIn=new BufferedReader(new InputStreamReader(System.in));
    int port;
    
    if(args.length<2)
    {
      System.out.println("Sintaxis de llamada: java EcoCliente <host> <puerto>");
      System.exit(-1);
    }
    port=Integer.valueOf(args[1]).intValue();
    try 
    {
      sock = new Socket(args[0], port);
      out = new PrintWriter(sock.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      System.out.println("Comienza a escribir:");
      while ((str = stdIn.readLine()) != null)
      {
        out.println(str);
        System.out.println("eco: " + in.readLine());
      }
      out.close();
      in.close();
      stdIn.close();
      sock.close();
    } 
    catch (UnknownHostException e)
    {
      System.err.println("No se nada del host "+args[0]);
      System.exit(1);
    }
    catch (IOException e) 
    {
      System.err.println("No pude conectarme a "+args[0]+port);
      System.exit(1);
    }
  }
}