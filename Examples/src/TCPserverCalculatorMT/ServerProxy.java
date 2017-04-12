package TCPserverCalculatorMT;

/*-------------------------------------------------------------------------
  Clase que emplea el Servidor de C�lculo para independizarse del protocolo
 particular empleado
 -------------------------------------------------------------------------*/
import java.io.*;
import java.net.*;

public class ServerProxy {
	public static int connectionClosed = -1;
	public static int nulOperation = 0;
	public static int factorial = 1;
	public static int mcd = 2;

	protected Socket sock;
	protected InputStream in;
	protected OutputStream out;

	public ServerProxy(Socket s) throws IOException {
		sock = s;
		in = sock.getInputStream();
		out = sock.getOutputStream();
	}

	public boolean respond(int n) {
		byte[] bResult;

		try {
			bResult = (n + "$").getBytes();
			out.write(bResult);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean respondNull() {
		byte[] bResult;

		try {
			bResult = "$".getBytes();
			out.write(bResult);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public int waitCommand(int[] operandos) {
		byte[] comando;
		int operacion;

		if ((comando = waitFrame((byte) '$')) == null)
			return connectionClosed;
		operacion = parseCommand(comando, operandos);
		return operacion;
	}

	protected int parseCommand(byte[] comando, int[] operandos) {
		int i, j, numOp = 0, operacion;
		String str;

		i = 0;
		while (comando[i] != ' ')
			i++;
		str = new String(comando, 0, i);
		if (str.compareTo("gcd") == 0)
			operacion = mcd;
		else if (str.compareTo("factorial") == 0)
			operacion = factorial;
		else
			operacion = nulOperation;
		if (operacion == mcd) {
			j = ++i;
			while (comando[i] != ' ')
				i++;
			str = new String(comando, j, i - j);
			operandos[numOp++] = Integer.valueOf(str).intValue();
		}
		j = ++i;
		while (comando[i] != '$')
			i++;
		str = new String(comando, j, i - j);
		operandos[numOp++] = Integer.valueOf(str).intValue();
		return operacion;
	}

	protected byte[] waitFrame(byte cFin) {
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