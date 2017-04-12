package FTPserverSimple;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Operador extends Thread{

	protected Socket s;
	String ficheroOriginal;//="D:\\MU\\3.maila\\2.sehilea\\sistemas distribuidos\\pruebak\\original.txt";
	int port;
	OutputStream out = null;
	InputStream in = null;
	boolean irakurrita=false;
	
	
	  public Operador(Socket sock){
	    this.s=sock;
	    
	  }
	  
	  public void run(){
			byte bufferNomFich[];
			bufferNomFich=new byte[1024];
			byte bufferDatos[];
			bufferDatos=new byte[1024];
			int luzera;
			
			try {
				out = s.getOutputStream();
				in = s.getInputStream();
				
				
				// Recibe por socket el fichero de lectura
				irakurrita=false;
				while(!irakurrita){
					bufferNomFich=esperarTrama((byte)'$');
					ficheroOriginal = new String(bufferNomFich);
					
				}
				ficheroOriginal= ficheroOriginal.substring(0, ficheroOriginal.length() - 1);;
				System.out.println("ha llegado el nombre del fichero");
				System.out.println("fichero original nombre: "+ficheroOriginal);
					
				  
		        // Se abre el fichero original para lectura
		       FileInputStream fileInput = new FileInputStream(ficheroOriginal);
		       BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
		       if(fileInput!=null){
		    	   // Se escribe por sockets
			       
			       while((luzera=bufferedInput.read(bufferDatos))!=-1){
			    	   
			    	   bufferedInput.read(bufferDatos, 0, luzera);
			    	   out.write(bufferDatos, 0, luzera);
			    	   System.out.println(new String(bufferDatos));
			    	  
			       }
			       out.write(('$'));
		    	   
		       }
		       else{
		    	   System.out.println("no se puede abrir el fichero");
		    	   out.write(Byte.valueOf(null));
		       }
		       bufferedInput.close();
		       s.close();
		       System.out.println("se ha mandado el fichero de origen");
			   
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
		    }
		    catch(IOException e){}
		    return null;
		  }
	  
	  
}
