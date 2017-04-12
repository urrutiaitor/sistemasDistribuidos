package TCPserverCalculatorMT;

import java.io.*;
import java.net.*;

class Worker extends Thread {
	protected Socket sock;
	protected int numWorker;
	protected WorkerManager jefe;
	protected boolean ordenFinalizar = false;

	public Worker(int numWorker, WorkerManager jefe) {
		this.numWorker = numWorker;
		this.jefe = jefe;
	}

	synchronized boolean isIdle() {
		return (sock == null);
	}

	synchronized void assignClient(Socket sock) {
		this.sock = sock;
		notify();
	}

	public void run() {
		while (!ordenFinalizar) {
			synchronized (this) {
				try {
					System.out.println("Worker " + numWorker + " Waiting");
					if (sock == null)
						wait();
				} catch (InterruptedException e) {
				}
				System.out.println("Worker " + numWorker + " awakened");
			}
			if (sock != null) {
				handleClient();
				sock = null;
				jefe.avisarLibre(numWorker);
			}
		}
		System.out.println("baja worker " + numWorker);
	}

	synchronized void finish() {
		ordenFinalizar = true;
		this.notify();
	}

	protected void handleClient() {
		int operacion, result;
		int[] operands = new int[2];
		ServerProxy sProxy;

		try {
			sProxy = new ServerProxy(sock);
			while ((operacion = sProxy.waitCommand(operands)) != ServerProxy.connectionClosed) {
				switch (operacion) {
				case 2:
					result = gcd(operands[0], operands[1]);
					sProxy.respond(result);
					break;
				case 1:
					result = factorial(operands[0]);
					sProxy.respond(result);
					break;
				default:
					sProxy.respondNull();
				}
			}
			;
			sock.close();
		} catch (IOException e) {
		}
	}

	public static int gcd(int a, int b) {
		int tmp;

		while ((tmp = a % b) != 0) {
			a = b;
			b = tmp;
		}
		return b;
	}

	public static int factorial(int n) {
		int i, f = 1;

		for (i = 2; i <= n; i++)
			f *= i;
		return f;
	}
}