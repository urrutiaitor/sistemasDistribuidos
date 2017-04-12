package TCPserverCalculatorMT;

import java.io.IOException;
import java.net.*;

public class WorkerManager extends Thread {
	protected static int maxWorkers = 2;
	protected int numLibres;
	protected Worker[] workerPool;
	protected boolean endOrder;
	ServerSocket s;

	public WorkerManager(int port) throws IOException {
		int i;

		workerPool = new Worker[maxWorkers];
		numLibres = maxWorkers;
		endOrder = false;
		for (i = 0; i < maxWorkers; i++) {
			workerPool[i] = new Worker(i, this);
		}
		for (i = 0; i < maxWorkers; i++)
			workerPool[i].start();
		s = new ServerSocket(port);
	}

	public void run() {
		Socket c;

		while (!endOrder) {
			try {
				waitWorkerAvailability();
				if (!endOrder) {
					c = s.accept();
					System.out.println("new client");
					allocateClient(c);
				}
			} catch (Exception e) {
			}
		}
	}

	public synchronized void waitWorkerAvailability() throws InterruptedException {
		while ((numLibres == 0) && (!endOrder))
			wait();
	}

	public synchronized void avisarLibre(int numWorker) {
		numLibres++;
		notify();
	}

	public void finish() throws IOException {
		int i;

		s.close();
		for (i = 0; i < maxWorkers; i++)
			workerPool[i].finish();
		System.out.println("ACABANDO JEFE");
		synchronized (this) {
			endOrder = true;
			notify();
		}
		for (i = 0; i < maxWorkers; i++) {
			try {
				workerPool[i].join();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Workers' manager finished");
	}

	public synchronized boolean allocateClient(Socket sock) {
		int i;

		for (i = 0; i < maxWorkers; i++)
			if (workerPool[i].isIdle()) {
				numLibres--;
				workerPool[i].assignClient(sock);
				return true;
			}
		return false;
	}
}