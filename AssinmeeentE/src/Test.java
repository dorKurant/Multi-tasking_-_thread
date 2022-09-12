import java.util.Vector;

public class Test {

	private int id;
	private int classNumber;
	private String date;
	private String[] questions = new String[20];
	private boolean[] answers = new boolean[20]; //the student's answers
	private int trueAnswer=0; //count the correct answers of the student
	private double gradeNoFactor; //student's grade without factor 
	private double gradeWithFactor; //student's grade with factor 
	private double finalGrade; //grade with factor and include assignments 
	private boolean ifSignedTest; //if the Proctor signed on the test
	private boolean ifCheckedOnce; //if one of the Teaching Assistant checked the test
	private boolean ifCheckedTwice; //if the other Teaching Assistant checked the test
	private boolean ifGaveFactor; //if the Lecturer gave a factor
	private boolean ifConfirmedTest; //if the Lecturer confirmed the test
	private boolean ifGaveFinalGrade; //if the Exercise Checker gave a final grade include assignments
	private boolean ifEnteredData; //if the IEM Secretary entered the data of the test to the data base
	private boolean ifScanned; //if the Exams Department Worker scanned the test
	private boolean ifWatched; //if the student watched the test after scanning

	public Test(int id, int classNumber, String[] questions, boolean[] answers) { //constructor
		this.id = id;
		this.classNumber = classNumber;
		this.questions = questions;
		this.answers = answers;
		this.trueAnswer = 0;
		this.gradeNoFactor = 0;
		this.gradeWithFactor = 0;
		this.finalGrade = 0;
		this.ifSignedTest = false;
		this.ifCheckedOnce = false;
		this.ifCheckedTwice = false;
		this.ifGaveFactor = false;
		this.ifConfirmedTest = false;
		this.ifGaveFinalGrade = false;
		this.ifEnteredData = false;
		this.ifScanned = false;
		this.ifWatched = false;
	}

	public void setIfSignedTest() {
		this.ifSignedTest = true;
	}

	public void setIfCheckedOnce() {
		this.ifCheckedOnce = true;
	}

	public boolean getIfCheckedOnce() {
		return ifCheckedOnce;
	}

	public void setIfCheckedTwice() {
		this.ifCheckedTwice = true;
	}

	public void setIfGaveFactor() {
		this.ifGaveFactor = true;
	}

	public void setIfConfirmedTest() {
		this.ifConfirmedTest = true;
	}

	public void setIfGaveFinalGrade() {
		this.ifGaveFinalGrade = true;
	}

	public void setIfEnteredData() {
		this.ifEnteredData = true;
	}

	public void setIfScaned() {
		this.ifScanned = true;
	}

	public void setIfWatched() {
		this.ifWatched = true;
	}

	public boolean[] getStudentsAnswers() { //the answers will be saved to the teaching assistants
		return this.answers;
	}

	public boolean getStudentsAnswer(int i) {
		return this.answers[i];
	}

	public void setGradeNoFactor() { //grade without factor = all the correct answers of the student*5
		this.gradeNoFactor = (this.trueAnswer*5);
	}

	public double getGradeNoFactor() {
		return this.gradeNoFactor;
	}

	public void setGradeWithFactor(double gradeWithFactor) { 
		this.gradeWithFactor = gradeWithFactor; 
	}

	public double getGradeWithFactor() {
		return this.gradeWithFactor;
	}

	public void setFinalGrade(double finalGrade) {
		this.finalGrade = finalGrade; 

	}

	public double getFinalGrade() {
		return this.finalGrade;
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setDate() {
		this.date = "15.02.2022"; //the date of Fatma test
	}

	public void setAnswer(int i,boolean answer) {
		answers[i] = answer;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	public void setTrueAnswer() { //all the student's true answers
		this.trueAnswer = this.trueAnswer+1;
	}

	public void reset() { //reset the first check of the first teaching assistant
		this.trueAnswer = 0;
		this.gradeNoFactor = 0;
	}
}