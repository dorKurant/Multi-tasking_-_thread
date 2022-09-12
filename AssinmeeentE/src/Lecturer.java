import java.util.Vector;
public class Lecturer implements Runnable{
	private String name;
	private Vector<Integer> excellents; //the id of the excellent students
	private Queue<Test> testTorTotal; //the queue between the teaching assistant and the lecturer
	private Queue<Test> testTorForExerciseChecker; //the queue between the lecturer and the Exercise Checker
	private boolean imFinished; //the lecturer finished his work
	private double sumBeforFactor=0;
	private double sumAfterFactor=0;
	private int numOfStudent;
	private boolean allTheTestsScanned;
	private InformationSystem info;
	private int count = 0; //count the tests 

	public Lecturer(String name, Vector<Integer> excellents,Queue<Test> testTorTotal,Queue<Test> testTorForExerciseChecker, int numOfStudent,InformationSystem info) { //constructor
		this.name = name;
		this.excellents = excellents;
		this.testTorTotal = testTorTotal;
		this.testTorForExerciseChecker = testTorForExerciseChecker;
		this.numOfStudent = numOfStudent;
		this.info = info;
		this.imFinished = false;
		this.sumBeforFactor = 0;
		this.sumAfterFactor = 0;
		this.allTheTestsScanned = false;
		this.count = 0;	
		this.excellents.clear();	
	}

	public void run() {

		while(count<numOfStudent) {
			Test t;
			t = testTorTotal.extract(); //the lecturer extract the next test
			try {
				Thread.sleep(1000); //simulation for working 
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			sumBeforFactor = sumBeforFactor + t.getGradeNoFactor();
			giveFactor(t); //give a factor to the test if needed 
			sumAfterFactor = sumAfterFactor + t.getGradeWithFactor();
			t.setIfConfirmedTest(); //the lecturer confirmed the test
			t.setIfGaveFactor(); //the lecturer gave factor
			areYouExcellents(t); //check if the student is excellent
			testTorForExerciseChecker.insert(t); //the lecturer pass the test to the exercise checker's queue
			count++;
		}	
		lastTestScanned(); //wait until all the tests will be scanned by the EDWs
		letsTellEveryOne(); //the lecturer finished his work, will notify the teaching assistants, exercise checker, IEM secretary
		print(); //test is over

	}
	public void giveFactor(Test t) { //check if that student should get a factor and give a factor
		if((t.getGradeNoFactor()>=50) && (t.getGradeNoFactor()<=55))
			t.setGradeWithFactor(56);
		if(t.getGradeNoFactor()>56) {
			t.setGradeWithFactor(t.getGradeNoFactor()+5);
			if(t.getGradeWithFactor()>100)
				t.setGradeWithFactor(100);
		}
		if(t.getGradeNoFactor()<50)
			t.setGradeWithFactor(t.getGradeNoFactor());
	}
	
	public void areYouExcellents(Test t) { //check if the student is excellent
		if(t.getGradeWithFactor()>95) {
			int id;
			id = t.getId();
			excellents.add(id); //add the id to the vector of excellents
		}
	}
	public synchronized void didYouFinished() { //waiting room of the lecturer
		while(imFinished != true) {
			try {
				this.wait(); //relevant threads will wait until the lecturer will finish
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}}
	public synchronized void letsTellEveryOne() { //notify the relevant threads 
		imFinished = true;
		System.out.println("I'm finished my work");
		this.notifyAll();
	}
	public void print() {
		System.out.println("Test is over! Grades are published, and here are the results: \n"
				+""+ "number of students have tasted:"+" "+ numOfStudent 
				+" \n"+ "average befor factor:"+" "+ sumBeforFactor/numOfStudent 
				+" \n"+ "average after factor:"+" "+ sumAfterFactor/numOfStudent
				+"  \n"+"excellents student list:"+" "+ excellents.toString());
		SAT.totalWage();
	}
	public void lastTestScanned() { //wait until all the tests will be scanned by the EDWs
		while(allTheTestsScanned == false) {
		}
	}
	public void setLastTestScanned() { //EDW scanned the last test
		allTheTestsScanned = true;
	}
}