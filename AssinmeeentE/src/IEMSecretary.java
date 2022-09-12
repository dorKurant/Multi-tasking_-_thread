public class IEMSecretary implements Runnable {

	private String name;
	private Queue<Test> testTorForIEMSecretary; //the queue between the exercise checker to the IEM secretary
	private BoundedQueue<Test> testTorForEDW; //the queue between the IEM secretary to the EDW
	private boolean isYourAreaGrades; //grade's area of the secretary 
	private Lecturer lecturer;
	private int numOfStudent;

	public IEMSecretary(String name, Queue<Test> testTorForIEMSecretary, BoundedQueue<Test> testTorForEDW, boolean isYourAreaGrades, Lecturer lecturer, int numOfStudent) { //constructor
		this.name = name;
		this.testTorForIEMSecretary = testTorForIEMSecretary;
		this.testTorForEDW = testTorForEDW;
		this.isYourAreaGrades = isYourAreaGrades;
		this.lecturer = lecturer; //pointing
		this.numOfStudent = numOfStudent;
	}

	public void run() {
		while(SAT.counterTestsForIEMSecretary<numOfStudent-1) { //2 IEM secretaries run at the same time 
			Test t;
			t = this.testTorForIEMSecretary.extract(); //the IEM secretary extract the next test
			boolean isUp70; //is the grade is up to 70
			if(t.getFinalGrade() >= 70) 
				isUp70 = true;
			else
				isUp70 = false;
			if(isYourAreaGrades == isUp70) { //the grade is at the secretary's area, she needs to enter the data 
				simulateRun(isUp70); //sleeping time of each secretary
				t.setIfEnteredData(); //the secretary entered the data of the test to the data base
				System.out.println("Student:" + " " + t.getId()); 
				System.out.println("The student's final grade is:" + " " + t.getFinalGrade()); 
				testTorForEDW.insert(t); //the secretary pass the test to the EDW's queue
				SAT.countTheTestsForIEMSecretary(); //count the tests in the queue
			}
			else {
				testTorForIEMSecretary.insert(t); //return the test to the queue if the grade isn't at the secretary's area
			}
		}
		ifNotNumOfStudent(); //if the number of students doesn't get to the maximum
		this.lecturer.didYouFinished(); //waiting room of the lecturer
	}

	public synchronized void ifNotNumOfStudent() {
		if(SAT.counterTestsForIEMSecretary == numOfStudent) {
			Test t;
			t = this.testTorForIEMSecretary.extract();
			t.setIfEnteredData(); //the secretary entered the data of the test to the data base
			testTorForEDW.insert(t); //the secretary pass the test to the EDW's queue
		}
		else
			SAT.counterTestsForIEMSecretary++; 
	} 

	public synchronized void simulateRun(boolean isUp70) { //simulate sleeping time of each secretary
		if(isUp70)
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		else
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
