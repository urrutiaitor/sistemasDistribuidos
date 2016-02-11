package echoMultiple;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class EcoServ {
	public static void main(String args[]) throws IOException {
    String strIn = null, strOut = null;
    int port;
    
    ArrayList<Conn> connList = new ArrayList<Conn>();

    ServerSocket s;

    if(args.length<1)
    {
      System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
      System.exit(-1);
    }
    port = Integer.valueOf(args[0]).intValue();
    s = new ServerSocket(port);
    
    do {
    	
    	Socket c = s.accept();
    	BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
    	OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());
    	
    	connList.add(new Conn(in, out, c));
    	
    	for (int i = 0; i < connList.size(); i++) {
    		while((strIn=connList.get(i).getIn().readLine())!=null) {
        		System.out.println("recibo "+strIn);
                strOut=strIn.toUpperCase();
                connList.get(i).getOut().write(strOut+'\n');
                connList.get(i).getOut().flush();
                System.out.println("\trespondo: "+strOut);
                
                if (strIn.toUpperCase() != "EXIT") break;
            };
    	}
    	
    	
    	
    } while (strIn.toUpperCase() != "EXIT");
    
    
  }
}