package BasicSocket;

/*----------------------------------------------------------------------
 Al invocarlo deber� especificar en la l�nea de comandos la direcci�n IP
y el puerto del host al que deseas conectarte. P.e.:

  java EcoCliente 172.17.121.177 8888
-----------------------------------------------------------------------*/
import java.io.*;
import java.net.*;


class HttpClientWithSockets
{
  public static void main(String[] args) throws IOException 
  {
    BufferedReader stdIn=new BufferedReader(new InputStreamReader(System.in));
    FileInputStream in;
    FileOutputStream out;
    InputStream sockIn;
    OutputStream sockOut;
    File fich;
    int port, realLen;
    InetAddress dirIP;
    byte[] buffer=new byte[1024];
    Socket sock;
        
    if(args.length<4)
    {
      System.out.println("Sintaxis de llamada: java HttpClientWithSockets <host> <puerto> <ficheroRequest> <ficheroRespuesta> ");
      System.exit(-1);
    }
    try
    {
      fich=new File(args[2]);
      in=new FileInputStream(fich);
      realLen=in.read(buffer, 0, buffer.length);
      in.close();
      port=Integer.valueOf(args[1]).intValue();
      dirIP=InetAddress.getByName(args[0]);
      sock = new Socket(dirIP, port);
      sockIn=sock.getInputStream();
      sockOut=sock.getOutputStream();

      fich=new File(args[3]);
      out=new FileOutputStream(fich);
      sockOut.write(buffer, 0, realLen);
      while((realLen=sockIn.read(buffer))!=-1)
    	 out.write(buffer,0,realLen);
      out.close();
      sockIn.close();
      sockOut.close();
      
    }
    catch(Exception e)
    {
      System.out.println("algo casc�");
      e.printStackTrace();
    }
  }
  
 
}