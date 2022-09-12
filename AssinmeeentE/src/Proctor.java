import java.math.*;
import java.util.Vector;

public class Proctor implements Runnable{
	private String name;
	private int age;
	private int numOfStudent;
	private Queue<Student> sTor; //the students queue
	private Queue<Test> testTor1; //the first test's queue for the first teaching assistant
	private Queue<Test> testTor2; //the second test's queue for the second teaching assistant
	
	public Proctor(String name, int age, int numOfStudent,Queue<Student> sTor, Vector <Test> allTheTests,Queue<Test> testTor1,Queue<Test> testTor2) { //constructor
		this.name = name;
		this.age = age;
		this.numOfStudent = numOfStudent;
		this.sTor = sTor;
		this.testTor1 = testTor1;
		this.testTor2 = testTor2;
	}

	public void run() {
		while(SAT.counterStudentsForProctors<numOfStudent-2) { //3 proctors run at the same time 
			Student s;
			s = this.sTor.extract(); //the proctor extract the next student
			signTheTest(s); //the proctor signs the test
			s.getMyTest().setClassNumber(s.getExamClassNumber()); //update the class number on the test
			sendToTestTor(s.getMyTest()); //insert the test to the shorter queue
			SAT.countTheStudentsForProctors(); //count the students in the queue
		}
	}

	public void signTheTest(Student s) { 
		int sleepTime=(int)(Math.random()*3000)+1000; //time of signing a test 
		try { 
			Thread.sleep((long)(sleepTime));
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		s.getMyTest().setIfSignedTest(); //the test was signed by the proctor
	}

	public void sendToTestTor(Test t) { //insert the test to the shorter queue of the teaching assistants
		if(testTor1.getLength() >= testTor2.getLength()) {
			testTor2.insert(t);
		}
		else
			testTor1.insert(t);
	}
}