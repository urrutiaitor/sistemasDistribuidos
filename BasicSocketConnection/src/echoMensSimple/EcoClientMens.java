package echoMensSimple;

import java.io.*;
import java.net.*;

class EcoClientMens
{
  public static void main(String[] args) throws IOException 
  {
    DatagramSocket c ;
    String str;
    BufferedReader stdIn=new BufferedReader(new InputStreamReader(System.in));
    int port;
    InetAddress address;
    byte[] bufOut,bufIn=new byte[256];
    DatagramPacket pOut,pIn;
    
    if(args.length<2)
    {
      System.out.println("Sintaxis de llamada: java EcoServ <host> <puerto>");
      System.exit(-1);
    }
    address=InetAddress.getByName(args[0]);
    port=Integer.valueOf(args[1]).intValue();
    c=new DatagramSocket();
    do
    {
      str = stdIn.readLine();
      bufOut=str.getBytes(); 
      System.out.println("bufOut.lenth: "+bufOut.length);
      pOut=new DatagramPacket(bufOut,bufOut.length,address,port); 
      c.send(pOut);
      pIn=new DatagramPacket(bufIn,bufIn.length);
      c.receive(pIn);
      System.out.println(""+bufIn.length+" "+pIn.getLength());
      if(bufIn==pIn.getData()) System.out.println("OK");
      str=new String(pIn.getData(),0,pIn.getLength());
      System.out.println("eco: " + str);
    }while(!str.equals("QUIT"));
    c.close();
  }
}