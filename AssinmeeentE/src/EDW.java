public class EDW implements Runnable {

	private String name;
	private BoundedQueue<Test> testTorForEDW; //the queue between the IEM secretary to the EDW
	private InformationSystem info;
	private Lecturer lecturer;
	private int numOfStudent;
	private int numOfEDW; //GUI input

	public EDW(String name, BoundedQueue<Test> testTorForEDW, InformationSystem info,Lecturer lecturer, int numOfStudent,int numOfEDW) { //constructor
		this.name = name;
		this.testTorForEDW = testTorForEDW;
		this.info = info;
		this.lecturer = lecturer;
		this.numOfEDW = numOfEDW;
		this.numOfStudent = numOfStudent;
	}

	public void run() {
		while(SAT.counterTestsForEDW<numOfStudent-numOfEDW+1) { //1-3 EDWs run at the same time
			Test t;
			t = this.testTorForEDW.extract();
			t.setIfScaned(); //the test was scanned by the EDW
			info.insertTests(t);  //insert the test to the information system, sleeping time at the method
			System.out.println("Exam Scanned for" + " "+ t.getId());
			SAT.countTheTestsForEDW(); //count the tests in the queue
		}
		lecturer.setLastTestScanned();	//EDW inform the lecturer that the last test was scanned
	}
}