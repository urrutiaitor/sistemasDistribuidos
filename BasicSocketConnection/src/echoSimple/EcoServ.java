package echoSimple;

import java.io.*;
import java.net.*;

public class EcoServ
{
  public static void main(String args[]) throws IOException
  {
    BufferedReader in;
    OutputStreamWriter out;
    String strIn,strOut;
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
    c = s.accept();
    in=new BufferedReader(new InputStreamReader(c.getInputStream()));
    out=new OutputStreamWriter(c.getOutputStream());
    while((strIn=in.readLine())!=null)
    {
      System.out.println("recibo "+strIn);
      strOut=strIn.toUpperCase();
      out.write(strOut+'\n');
      out.flush();
      System.out.println("\trespondo: "+strOut);
    };
    out.close();
    in.close();
    c.close();
    s.close();
  }
}