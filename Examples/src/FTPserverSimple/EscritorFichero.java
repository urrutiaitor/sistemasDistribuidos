package FTPserverSimple;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EscritorFichero extends Thread {

	String ficheroCopia="D:\\MU\\3.maila\\2.sehilea\\sistemas distribuidos\\pruebak\\copia.m";
	String ficheroOriginal="D:\\MU\\3.maila\\2.sehilea\\sistemas distribuidos\\pruebak\\fitellipse.m";
	Socket s;
	int port;
	OutputStream out = null;
	InputStream in = null;
	boolean irakurrita=false;
	
	public EscritorFichero(String args[]){
		
		if (args.length < 2) {
			System.out
					.println("Sintaxis de llamada: java cliente <host> <puerto>");
			System.exit(-1);
		}
		port = Integer.valueOf(args[1]).intValue();
		try {
			s = new Socket(args[0],port);
			
			
			ejecutar();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ejecutar(){
		byte bufferNomFich[];
		bufferNomFich=new byte[1024];
		byte bufferDatos[];
		bufferDatos=new byte[1024];
		int luzera;
		try {
			out = s.getOutputStream();
			in = s.getInputStream();
			
		    // Se le manda el fichero que se quiere copiar
			bufferNomFich=(ficheroOriginal+'$').getBytes();
			out.write(bufferNomFich, 0, bufferNomFich.length);
			
			System.out.println("se ha mandado el nombre del fichero");
		   
		   // Se abre el fichero donde se har� la copia
		   FileOutputStream fileOutput = new FileOutputStream (ficheroCopia);
		   BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);
		    
		   
		   // Se copia en fichero de destino lo recibido por sockets
		   irakurrita=false;
		   
		   while(!irakurrita){
			   bufferDatos=esperarTrama((byte)'$');
			   if(bufferDatos==(null)){
				   System.out.println("no se ha podido acceder al archivo");
				   break;
				   
			   }else{
				   if(irakurrita){
					   bufferedOutput.write(bufferDatos, 0, bufferDatos.length-1);
					   System.out.println("se ha copiado el fichero de origen");
				   }else{
					   bufferedOutput.write(bufferDatos, 0, bufferDatos.length);
				   }
			   }
		   }
	       bufferedOutput.close();
	       s.close();
	       
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	}
	
	protected byte[] esperarTrama(byte cFin)
	  {
	    byte buf[]=new byte[1024];
	    int nBytes=0,n;
	    
	    try
	    {
	      while((n=in.read(buf,nBytes,1024-nBytes))>0)
	      {
	        nBytes+=n;
	        if(buf[nBytes-1]==cFin)
	        {
	        	irakurrita=true;
	           byte[] returnBuf=new byte[nBytes];
	           for(int i=0;i<nBytes;i++) returnBuf[i]=buf[i];
	           return returnBuf;
	        }
	      }
	      if(n==0){
	    	  byte[] returnBuf=new byte[nBytes];
	    	  for(int i=0;i<nBytes;i++) returnBuf[i]=buf[i];
	    	  return returnBuf;
	      }
          
	    }
	    catch(IOException e){}
	    return null;
	  }
	
	public static void main(String[] args){
		EscritorFichero ariketa = new EscritorFichero(args);
		
	}
}
