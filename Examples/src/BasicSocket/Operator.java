package BasicSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


class Operator extends Thread
{
  protected Socket sock;

	public Operator(Socket sock) {
		this.sock = sock;
	}
  public void run()
  {
    String strIn,strOut,strIPCliente;
    BufferedReader in=null;
    OutputStreamWriter out=null;
    
    try
    {
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
    catch(IOException e){}
  }
}