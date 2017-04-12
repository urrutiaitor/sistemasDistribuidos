package TCPsimpleCalculatorST;
/*---------------------------------------------------------------------------
  Clase que una aplicaci�n cliente puede emplear para acceder al servicio de
 C�lculo. Act�a como Proxy o delegado de �ste.
 --------------------------------------------------------------------------*/

import java.io.*;
import java.net.*;

public class ClientProxy {
	protected Socket sock;
	protected InputStream in;
	protected OutputStream out;

	public ClientProxy(InetAddress dirIP, int port) throws IOException {
		sock = new Socket(dirIP, port);
		in = sock.getInputStream();
		out = sock.getOutputStream();
	}

	public int gcd(int n1, int n2) throws IOException {
		byte[] buf;
		String str;

		str = "gcd " + n1 + ' ' + n2 + '$';
		out.write(str.getBytes());
		/*
		 * str="gcd "+n1; out.write(str.getBytes()); str=" "+n2+'$';
		 * out.write(str.getBytes());
		 */
		buf = esperarTrama((byte) '$');
		str = new String(buf, 0, buf.length - 1);
		n1 = Integer.valueOf(str).intValue();
		return n1;
	}

	public int factorial(int n) throws IOException {
		byte[] buf;
		String str;

		str = "factorial " + n + '$';
		out.write(str.getBytes());
		buf = esperarTrama((byte) '$');
		str = new String(buf, 0, buf.length - 1);
		n = Integer.valueOf(str).intValue();
		return n;
	}

	protected byte[] esperarTrama(byte cFin) {
		byte buf[] = new byte[256];
		int nBytes = 0, n;

		try {
			while ((n = in.read(buf, nBytes, 256 - nBytes)) > 0) {
				nBytes += n;
				if (buf[nBytes - 1] == cFin) {
					byte[] returnBuf = new byte[nBytes];
					for (int i = 0; i < nBytes; i++)
						returnBuf[i] = buf[i];
					return returnBuf;
				}
			}
		} catch (IOException e) {
		}
		return null;
	}
}