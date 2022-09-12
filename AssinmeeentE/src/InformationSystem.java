import java.util.Vector;

public class InformationSystem{

	private Vector<Test> scanedByEDW; //vector of tests that scanned by EDW workers

	public InformationSystem() { //constructor
		scanedByEDW = new Vector(); //containment
		this.scanedByEDW.clear();
	}

	public synchronized void insertTests(Test t) { 
		scanedByEDW.add(t); //add the test that scanned to the vector
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.notifyAll(); //notify all the students that a test was scanned
	}

	public synchronized void didMyTestScanned(long id) { //student thread will wait until his test will be scanned 
		boolean isScanned = false; //student's test has not scanned yet
		while(isScanned != true) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try { 
					Thread.sleep(2000); 
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			for(int i=0;i<scanedByEDW.size();i++) {
				if(scanedByEDW.elementAt(i).getId() == id) //the thread will check if his own test already scanned
					isScanned = true;
			}
		}
	}
}