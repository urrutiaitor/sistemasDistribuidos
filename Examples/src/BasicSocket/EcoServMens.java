package BasicSocket;

/*----------------------------------------------------------------------
  Programa que esperando la llegada de datagramas que contengan un string
responden con un datagrama conteniendo el mismo string convertido a
may�scula. El programa finaliza cuando recibe la palabra "quit" y 
responde con "QUIT". Se puede comprobar junto con EcoClientMens.java
-----------------------------------------------------------------------*/
import java.io.*;
import java.net.*;

public class EcoServMens
{
  public static void main(String args[]) throws IOException
  {
    String strIn,strOut;
    int port;
    DatagramSocket s;
    byte[] buf = new byte[256], respuesta;
    DatagramPacket pIn,pResp;
    

    
    if(args.length<1)
    {
      System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
      System.exit(-1);
    }
    port=Integer.valueOf(args[0]).intValue();
    s=new DatagramSocket(port);
    do
    {
      pIn= new DatagramPacket(buf, buf.length);
      s.receive(pIn);
      System.out.println(""+buf.length+""+pIn.getLength());
      strIn=new String(pIn.getData(),0,pIn.getLength());
      System.out.println("from "+pIn.getAddress()+':'+pIn.getPort()+"\n\t"+strIn.length()+" bytes: "+strIn);
      strOut=strIn.toUpperCase();
      respuesta=strOut.getBytes();
      pResp=new DatagramPacket(respuesta,respuesta.length,
                               pIn.getAddress(),pIn.getPort());
      s.send(pResp);
      System.out.println("\tresponding with: "+strOut);
    }while(!strOut.equals("QUIT"));
    s.close();
  }
}