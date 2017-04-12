package FTPserverSimple;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class LectorFichero {

	private final static int maxClients=3;
	
	int port,nClientes=0,i;
    ServerSocket s;
    Socket c;
    Operador operatorPool[];	
	
	public LectorFichero(String args[]) throws IOException{
		operatorPool=new Operador[maxClients];
	
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
	      operatorPool[nClientes]=new Operador(c);
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

	public static void main(String[] args){
		try {
			new LectorFichero(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
