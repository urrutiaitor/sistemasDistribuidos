package UDPcalculatorServerMT;

import java.io.*;
import java.net.*;

class Worker extends Thread {
	protected DatagramPacket pIn;
	protected DatagramSocket s;
	protected WorkerBuffer workersBuf;
	protected boolean ordenFinalizar;
	protected int numWorker;

	public Worker(int numWorker, DatagramSocket s, WorkerBuffer workersBuf) {
		this.s = s;
		this.workersBuf = workersBuf;
		this.numWorker = numWorker;
		ordenFinalizar = false;
	}

	synchronized void setTask(DatagramPacket pIn) {
		this.pIn = pIn;
		notify();
	}

	public void run() {
		try {
			workersBuf.addWorker(this);
		} catch (InterruptedException e) {
			System.out.println("Worker " + numWorker + " acabando porque no le admiten");
			return;
		}

		while (!ordenFinalizar || (pIn != null)) {
			try {
				synchronized (this) {
					if ((pIn == null) && (!ordenFinalizar))
						wait();
				}
				if (pIn != null) {
					sleep(10000);
					atenderCliente();
					pIn = null;
					workersBuf.addWorker(this);
				}
			} catch (InterruptedException e) {
			} catch (IOException e) {
				System.out.println(numWorker + " langilean arazoak irteerako mezuarekin");
			}
		}
		System.out.println("baja worker " + numWorker);
	}

	public void finish() {
		ordenFinalizar = true;
		this.interrupt();
	}

	protected void atenderCliente() throws IOException {
		MsgContent m;
		int operacion, result, operandos[] = new int[2];

		m = new MsgContent(pIn.getData(), pIn.getLength());
		operacion = m.extractOperation(operandos);
		switch (operacion) {
		case 2:
			result = gcd(operandos[0], operandos[1]);
			m = MsgContent.createMsgForResult(result);
			break;
		case 1:
			result = factorial(operandos[0]);
			m = MsgContent.createMsgForResult(result);
			break;
		}
		s.send(m.putInDatagram(pIn.getAddress(), pIn.getPort()));
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