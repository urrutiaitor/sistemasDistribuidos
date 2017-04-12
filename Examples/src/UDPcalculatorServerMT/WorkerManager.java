package UDPcalculatorServerMT;

import java.io.IOException;
import java.net.*;

public class WorkerManager extends Thread {
	protected WorkerBuffer iddleWorkers;
	protected int port;
	protected boolean ordenFinalizar;
	protected DatagramSocket s;
	protected Worker[] workers;

	public WorkerManager(int port) {
		int i;

		try {
			iddleWorkers = new WorkerBuffer();
			this.port = port;
			ordenFinalizar = false;
			s = new DatagramSocket(port);
			workers = new Worker[WorkerBuffer.MAX_CAPACITY];
			for (i = 0; i < WorkerBuffer.MAX_CAPACITY; i++)
				workers[i] = new Worker(i, s, iddleWorkers);
			for (i = 0; i < WorkerBuffer.MAX_CAPACITY; i++)
				workers[i].start();
		} catch (SocketException e) {
		}
	}

	public void run() {
		DatagramPacket pIn;
		byte[] tmpBuf, buf = new byte[256];
		Worker w;
		int i;

		while (!ordenFinalizar) {
			try {
				w = iddleWorkers.getAvailable();
				pIn = new DatagramPacket(buf, buf.length);
				s.receive(pIn);
				tmpBuf = new byte[pIn.getLength()];
				for (i = 0; i < pIn.getLength(); i++)
					tmpBuf[i] = buf[i];
				w.setTask(new DatagramPacket(tmpBuf, tmpBuf.length, pIn.getAddress(), pIn.getPort()));
			} catch (InterruptedException e) {
				ordenFinalizar = true;
			} catch (IOException e) {
				ordenFinalizar = true;
			}
		}
		System.out.println("jefe finalizado");
	}

	public void finish() {
		int i;
		ordenFinalizar = true;
		interrupt();

		for (i = 0; i < WorkerBuffer.MAX_CAPACITY; i++)
			workers[i].finish();
		try {
			for (i = 0; i < WorkerBuffer.MAX_CAPACITY; i++)
				workers[i].join();
		} catch (InterruptedException e) {
		}
		s.close();
	}
}