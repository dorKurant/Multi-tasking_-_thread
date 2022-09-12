import java.io.Reader;
import java.math.*;
import java.util.Vector;

public class Student implements Runnable{ 
	private Queue<Student> studentTor;
	private int id;
	private String name;
	private int classNumber;
	private double probabilityCorrect; //the probability to correct answer
	private double questionAnswerTime; //rate of answer 
	private Double [] grades; //the 4 grades of the student
	private Test myTest; //student's test
	private InformationSystem info;
	boolean[] trueAnswers; //array of the student's answers

	public Student(int id, String name, int classNumber, double questionAnswerTime, double probabilityCorrect,Double[] grades,Queue<Student> studentTor, Test myTest, InformationSystem info,boolean[] trueAnswers) { //constructor
		this.id = id;
		this.name = name;
		this.classNumber = classNumber;
		this.questionAnswerTime = questionAnswerTime;
		this.probabilityCorrect = probabilityCorrect;
		this.grades = grades;
		this.studentTor = studentTor; //pointing
		this.myTest = myTest;
		this.info = info;
		this.trueAnswers = trueAnswers;
		
	}

	public void run() {
		myTest.setId(this.id); //set the id on the test
		myTest.setDate(); //set the date on the test 
		practice(); //simulate a test 
		studentTor.insert(this); //insert the student to a queue before they will give the test to the proctor
		info.didMyTestScanned(this.id); //enter the information system to check if the studen's test was scanned, if not-->wait
		myTest.setIfWatched(); //the student watched the test in the information system
	}

	public void practice() { //simulation of a test for every student
		int i=0;
		while(i<20) { //20 questions
			double rand = Math.random();
			if(rand <= probabilityCorrect) // random probability to correct answer
				myTest.setAnswer(i,trueAnswers[i]); //correct answer
			else
				myTest.setAnswer(i,inverse(trueAnswers[i])); //wrong answer

			try {
				Thread.sleep((long)(this.questionAnswerTime*1000)); //time of simulation a test
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}

	private boolean inverse(boolean answer) { //reverse true value to false and on the contrary 
		if(answer==true)
			return false;
		else
			return true;
	}

	public Double[] getGrades() {
		return this.grades;
	}

	public int getId() { 
		return this.id;
	}

	public Test getMyTest() {
		return myTest;
	}

	public int getExamClassNumber() {
		return classNumber;
	}

	public int hashCode() { //HashMap (Exercise Checker)
		return this.id;
	}

	public boolean equals(Object obj) { //HashMap 
		if(obj instanceof Student)
			return this.equals((Student)obj);
		else
			return false;
	}

	public boolean equals(Student s) { //HashMap
		return this.id==s.id;
	}
}