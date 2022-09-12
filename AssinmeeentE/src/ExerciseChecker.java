import java.math.*;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.Vector;


public class ExerciseChecker implements Wageable, Runnable{
	private String name;
	private double wage;
	private Map<Student, Double[]> studentsGrades = new HashMap<Student, Double[]>(); //HashMap - key - student, value - grades
	private Queue<Test> testTorForExerciseChecker; //the queue between the lecturer and the Exercise Checker
	private Queue<Test> testTorForIEMSecretary; //the queue between the lecturer and the IEM Secretary
	private Vector<Student> allTheStudent;
	private Student s;
	private Lecturer lecturer;
	private int count = 0;
	private int numOfStudent;

	public ExerciseChecker(String name, Map<Student, Double[]> studentsGrades, Queue<Test> testTorForExrciseChecker, Queue<Test> testTorForIEMSecretary,  Vector<Student> allTheStudent,Lecturer lecturer,int numOfStudent) { //constructor
		this.name = name;
		this.studentsGrades = studentsGrades;
		this.testTorForExerciseChecker = testTorForExrciseChecker;
		this.testTorForIEMSecretary = testTorForIEMSecretary;
		this.allTheStudent = allTheStudent;
		this.lecturer = lecturer;
		this.numOfStudent = numOfStudent;
		this.wage = 0;
		this.count = 0;
	}

	public void run() {
		while(count<numOfStudent) {
			Test t;
			t = testTorForExerciseChecker.extract(); //the exercise checker extract the next test
			Double assignmentsGrades = calculateGrade(t); //return the assignments grades for every student
			calculateGradeWithFactor(t,assignmentsGrades); //give the grade with factor
			t.setIfGaveFinalGrade(); //the exercise checker gave final grade to the student
			testTorForIEMSecretary.insert(t); //the exercise checker pass the queue to the IEM Secretary
			count++;
		}
		lecturer.didYouFinished(); //waiting room of the lecturer
	}

	public synchronized Double calculateGrade(Test t) {
		s = getStudentByTest(t);  
		Double[] grades = studentsGrades.get(s); //get the value of the student's grade 
		Double assignmentsGrade = (grades[0]*0.02)+(grades[1]*0.04)+(grades[2]*0.06)+(grades[3]*0.08); //calculate the assignments grade
		return assignmentsGrade;
	}
	
	public synchronized Student getStudentByTest(Test t) { //method that return the student that the test is own to him
		for(int i=0;i<allTheStudent.size();i++) {
			if(t.getId() == allTheStudent.elementAt(i).getId())
				s = allTheStudent.elementAt(i);
		}
		return s;
	}

	public synchronized void calculateGradeWithFactor(Test t,double assignmentsGrades) {
		double rand = (double)(Math.random()*1000)+2000; //time of ExerciseChecker's working
		wage = wage + (rand/1000); //calculate the ExerciseChecker's wage after any test
		try { 
			Thread.sleep((long)(rand));
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		t.setFinalGrade((t.getGradeWithFactor()*0.8)+assignmentsGrades); //set the final grade
	}
	
	public double getWage() { //implement the method of the wageable interface
		return this.wage;
	}
}