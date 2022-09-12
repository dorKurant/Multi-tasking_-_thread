import java.lang.Math;
import java.util.Vector;
public class TeachingAssistant implements Wageable, Runnable {

	private String name;
	private double wagePerSec;
	private boolean[] trueAnswers = new boolean[20]; //the true answers of the questions
	private Queue<Test> testTor1; //the first queue
	private Queue<Test> testTor2; //the second queue
	private Queue<Test> testTorTotal; //the queue that will be passed to the lecturer
	private double Perror; //GUI input
	private Lecturer lecturer;
	private int numOfStudent;

	public TeachingAssistant(String name, boolean[] trueAnswers, Queue<Test> testTor1,Queue<Test> testTor2, Queue<Test> testTorTotal, double Perror, Lecturer lecturer, int numOfStudent) { //constructor
		this.name = name;
		this.trueAnswers = trueAnswers; 
		this.testTor1 = testTor1;
		this.testTor2 = testTor2;
		this.testTorTotal = testTorTotal;
		this.Perror = Perror;
		this.lecturer = lecturer; //pointing
		this.numOfStudent = numOfStudent;
		this.wagePerSec = 0;
	}

	public void run() {
		while(SAT.counterTestsForTeachingAssistants<(numOfStudent*2)-1) { //2 teaching assistant that run at the same time 
			Test t;
			t = testTor1.extract(); //the teaching assistant extract the next test
			SAT.countTheTestsForTeachingAssistants(); //count the tests in the queue
			if(!t.getIfCheckedOnce()) { //if the test hasn't checked yet
				checkTests(t,this.Perror); //check tests by the first teaching assistant 
				t.setIfCheckedOnce(); //the test was checked by the first teaching assistant
				testTor2.insert(t); //the first teaching assistant pass the test to the second teaching assistant's queue 
			}
			else {
				t.reset(); //reset the first check of the first teaching assistant
				checkTests(t,(this.Perror/2)); //check tests by the second teaching assistant 
				t.setIfCheckedTwice(); //the test was checked by the second teaching assistant
				testTorTotal.insert(t); //the second teaching assistant pass the test to the lecturer's queue
			}
		}	
		this.lecturer.didYouFinished(); //waiting room of the lecturer
	}

	public void checkTests(Test t, double Perror) {
		double sleepTime = (double)(Math.random()*1500)+1000; //time of checking a test 
		this.wagePerSec = 3*(sleepTime/1000); //wage per seconds for each teaching assistant
		try {
			Thread.sleep((long)(sleepTime));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0;i<trueAnswers.length;i++) {
			boolean specificAns = trueAnswers[i]; //the true answer of one of the questions
			if(specificAns == t.getStudentsAnswer(i)) //correct answers
				t.setTrueAnswer();
			else {
				double randNum = Math.random();
				if(randNum <= Perror) { //the chance to get a wrong check
					t.setTrueAnswer(); //wrong answers get points like correct answers 
				}	
			}
		}
		t.setGradeNoFactor(); //give grade without factor
	}

	public double getWage() { //implement the method of the wageable interface
		return this.wagePerSec;
	}
}