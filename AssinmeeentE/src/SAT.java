import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class SAT { //the envelope class - create all objects, read from the file of students
	private Queue<Student> studentTor; 
	private Queue<Test> testTor1, testTor2, testTorTotal, testTorForExerciseChecker, testTorForIEMSecretary; //all the unbounded queues 
	private BoundedQueue<Test> testTorForEDW; 
	private Vector<Test> allTheTests; 
	private Vector<Student> allTheStudents;
	private boolean[] trueAnswers; //for student and teaching assistant
	private Lecturer lecturer; 
	private Vector<Integer> excellents; //the id of the excellent students
	private Map<Student, Double[]> studentsGrades; //HashMap - key - student, value - grades
	private String[] questions;
	private boolean[] answers; //the student's answers
	private InformationSystem info;
	public static int counterStudentsForProctors;
	public static int counterTestsForTeachingAssistants;
	public static int counterTestsForIEMSecretary;
	public static int counterTestsForEDW ;
	public static TeachingAssistant Lior;
	public static TeachingAssistant Maya;
	public static ExerciseChecker Ofri;
	private double Perror; //GUI input
	private int numOfEDW; //GUI input

	public SAT(double Perror, int numOfEDW) { //constructor
		this.Perror = Perror;
		this.numOfEDW = numOfEDW;
		studentTor = new Queue<Student>();
		allTheStudents = new Vector<Student>();
		testTor1 = new Queue<Test>();
		testTor2 = new Queue<Test>();
		testTorTotal = new Queue<Test>();
		allTheTests = new Vector<Test>();
		answers = new boolean[20];
		trueAnswers = new boolean[20];
		excellents = new Vector<Integer>();
		testTorForExerciseChecker = new Queue<Test>();
		testTorForIEMSecretary = new Queue<Test>();
		testTorForEDW = new BoundedQueue<Test>();
		studentsGrades = new HashMap<Student, Double[]>();
		info = new InformationSystem();
		counterTestsForEDW = 0;
		counterTestsForIEMSecretary = 0;
		counterTestsForTeachingAssistants = 0;
		counterStudentsForProctors = 0;
		trueAnswers(); //random true answers to the teaching assistant 
		setFile(); //read from file
		lecturer = new Lecturer("Roie",excellents,testTorTotal,testTorForExerciseChecker,allTheStudents.size(),info);
		EDWstart(numOfEDW);
		Proctor Sigalit = new Proctor("Sigalit",30,allTheStudents.size(),studentTor,allTheTests,testTor1,testTor2);
		Proctor Rene = new Proctor("Rene",25,allTheStudents.size(),studentTor,allTheTests,testTor1,testTor2);
		Proctor Rivka = new Proctor("Rivka",32,allTheStudents.size(),studentTor,allTheTests,testTor1,testTor2);
		Lior = new TeachingAssistant("Lior",trueAnswers,testTor1,testTor2,testTorTotal,Perror,lecturer,allTheStudents.size());
		Maya = new TeachingAssistant("Maya",trueAnswers,testTor2,testTor1,testTorTotal,Perror,lecturer,allTheStudents.size());
		Ofri = new ExerciseChecker("Ofri",studentsGrades,testTorForExerciseChecker,testTorForIEMSecretary,allTheStudents,lecturer,allTheStudents.size());
		IEMSecretary Esti = new IEMSecretary("Esti",testTorForIEMSecretary,testTorForEDW,true,lecturer,allTheStudents.size());
		IEMSecretary Hava = new IEMSecretary("Hava",testTorForIEMSecretary,testTorForEDW,false,lecturer,allTheStudents.size());
		Thread t1 = new Thread(Sigalit); //start threads
		t1.start();
		Thread t2 = new Thread(Rene);
		t2.start();
		Thread t3 = new Thread(Rivka);
		t3.start();
		Thread t4 = new Thread(Lior);
		t4.start();
		Thread t5 = new Thread(Maya);
		t5.start();
		Thread t10 = new Thread(lecturer);
		t10.start();
		Thread t6 = new Thread(Ofri);
		t6.start();
		Thread t7 = new Thread(Esti);
		t7.start();
		Thread t8 = new Thread(Hava);
		t8.start();
	}

	private void setFile() { //read text from file 
		File file = new File("StudentData.txt"); 
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file)); 
			String st = br.readLine(); 
			st = br.readLine(); //skip the first line 
			while(st != null) {
				String[] arr = st.split("\t"); //turn the next line to a new student
				int id = Integer.parseInt(arr[0]); 
				String name = arr[1]; 
				int testRoom = Integer.parseInt(arr[2]); 
				double questionAnswerTime = Double.parseDouble(arr[3]);
				double probabilityCorrect = Double.parseDouble(arr[4]);
				Double[] grades ={Double.parseDouble(arr[5]),Double.parseDouble(arr[6]),Double.parseDouble(arr[7]),Double.parseDouble(arr[8])};
				Test t = new Test(id,testRoom,questions,answers);
				Student s = new Student(id ,name ,testRoom, questionAnswerTime, probabilityCorrect ,grades,studentTor,t,info,trueAnswers);
				studentsGrades.put(s,grades); //HashMap
				Thread t0 = new Thread(s);
				allTheStudents.add(s); //all the students
				t0.start();
				st=br.readLine();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void trueAnswers() { //random true answers to the teaching assistant 
		for(int i=0;i<20; i++) {
			double rand = Math.random();
			if(rand < 0.5)
				trueAnswers[i] = true;
			else
				trueAnswers[i] = false;
		}
	}

	public void EDWstart(int numOfEDW) { //number of EDW workers
		for(int i=0;i<numOfEDW;i++) {
			EDW Sami = new EDW("Sami",testTorForEDW,info,lecturer,allTheStudents.size(),numOfEDW); 
			Thread t9 = new Thread(Sami);
			t9.start();
		}
	}

	public static void totalWage() { //total wage of all the wageable objects
		double sum = Lior.getWage()+Maya.getWage()+Ofri.getWage();
		System.out.println("The total wage is:" + " " +sum);
	}

	public static synchronized void countTheStudentsForProctors() { //count the students in the queue ,static function
		counterStudentsForProctors++;
	}

	public static synchronized void countTheTestsForTeachingAssistants() { //count the tests in the queue ,static function
		counterTestsForTeachingAssistants++;
	}
	public static synchronized void countTheTestsForIEMSecretary() { //count the tests in the queue ,static function
		counterTestsForIEMSecretary++;
	}

	public synchronized static void countTheTestsForEDW() { //count the tests in the queue ,static function
		counterTestsForEDW++;
	}
}