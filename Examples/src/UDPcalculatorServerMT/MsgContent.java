package UDPcalculatorServerMT;

import java.net.*;

public class MsgContent {
	protected byte[] content;
	public static int nullOperation = 0;
	public static int factorial = 1;
	public static int gcd = 2;

	public MsgContent(byte[] b) {
		int i;

		content = new byte[b.length];
		for (i = 0; i < b.length; i++)
			content[i] = b[i];
	}

	public MsgContent(byte[] b, int len) {
		int i;

		content = new byte[len];
		for (i = 0; i < len; i++)
			content[i] = b[i];
	}

	public DatagramPacket putInDatagram(InetAddress ipAddress, int port) {
		return new DatagramPacket(content, content.length, ipAddress, port);
	}

	public static MsgContent createMsgForGcd(int n1, int n2) {
		String str;

		str = "gcd " + n1 + ' ' + n2 + '$';
		return new MsgContent(str.getBytes());
	}

	public static MsgContent createMsgForFactorial(int n) {
		String str;

		str = "factorial " + n + '$';
		return new MsgContent(str.getBytes());
	}

	public static MsgContent createMsgForResult(int n) {
		String str;

		str = n + "$";
		return new MsgContent(str.getBytes());
	}

	public int getResult() {
		String str;
		int n;

		str = new String(content, 0, content.length - 1);
		n = Integer.valueOf(str).intValue();
		return n;
	}

	public int extractOperation(int[] operandos) {
		int i, j, numOp = 0, operacion;
		String str;

		i = 0;
		while (content[i] != ' ')
			i++;
		str = new String(content, 0, i);
		if (str.compareTo("gcd") == 0)
			operacion = gcd;
		else if (str.compareTo("factorial") == 0)
			operacion = factorial;
		else
			operacion = nullOperation;
		if (operacion == gcd) {
			j = ++i;
			while (content[i] != ' ')
				i++;
			str = new String(content, j, i - j);
			operandos[numOp++] = Integer.valueOf(str).intValue();
		}
		j = ++i;
		while (content[i] != '$')
			i++;
		str = new String(content, j, i - j);
		operandos[numOp++] = Integer.valueOf(str).intValue();
		return operacion;
	}
}