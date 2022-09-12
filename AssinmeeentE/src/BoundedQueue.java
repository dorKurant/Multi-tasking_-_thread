public class BoundedQueue<T> extends Queue<T> { //bounded buffer extends unbounded buffer
	private static int maxSize = 10;
	
	public BoundedQueue() { 
		super();
	}

	public synchronized void insert(T object) { //override 
		while(buffer.size() >= maxSize) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buffer.add(object);
		this.notifyAll();
	}
	public synchronized T extract() { //override 
		while(this.buffer.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.notifyAll();
		return buffer.remove(0);
	}
}