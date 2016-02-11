package echoMultiple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Conn {

	BufferedReader in;
    OutputStreamWriter out;
    
    Socket c;

	public Conn(BufferedReader in, OutputStreamWriter out, Socket c) {
		super();
		this.in = in;
		this.out = out;
		this.c = c;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public OutputStreamWriter getOut() {
		return out;
	}

	public void setOut(OutputStreamWriter out) {
		this.out = out;
	}

	public Socket getC() {
		return c;
	}

	public void setC(Socket c) {
		this.c = c;
	}
    
    public void close () {
    	try {
			in.close();
			out.close();
	    	c.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}
