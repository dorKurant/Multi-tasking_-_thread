import java.util.Vector;

public class Queue<T> {
	
	protected Vector<T> buffer;
	private int counter = 0;

	public Queue() {
		this.buffer = new Vector<T>(); //containment
	}

	public synchronized void insert(T object) {
		buffer.add(object);
		this.notifyAll();	
	}

	public synchronized T extract() { 
		while(this.buffer.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return buffer.remove(0);
	}
	
	public int getLength() { //get the size of the queue
		return this.buffer.size();
	}
	
	public synchronized void countTheTests() { //count the tests in the queue
		counter++;
	}
	
	public synchronized int getCounter() {
		return counter;
	}
}