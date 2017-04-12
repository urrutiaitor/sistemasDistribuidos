package UDPcalculatorServerMT;

public class WorkerBuffer {
	public final static int MAX_CAPACITY = 2;
	protected Worker[] workers;
	protected int cabeza, cola, numWorkers;

	protected WorkerBuffer() {
		workers = new Worker[MAX_CAPACITY];
	}

	protected synchronized void addWorker(Worker w) throws InterruptedException {
		while (numWorkers == MAX_CAPACITY)
			wait();
		workers[cabeza] = w;
		cabeza = (cabeza + 1) % MAX_CAPACITY;
		numWorkers++;
		notify();
	}

	protected synchronized Worker getAvailable() throws InterruptedException {
		Worker w;

		while (numWorkers == 0)
			wait();
		numWorkers--;
		w = workers[cola];
		cola = (cola + 1) % MAX_CAPACITY;
		notify();
		return w;
	}
}
