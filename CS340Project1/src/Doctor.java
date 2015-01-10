/**
 * Lewis Matos
 * CS 340
 * PROJECT 1
 * Professor Fluture
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;

public class Doctor implements Runnable {
	private static  LinkedTransferQueue<Patient> Pshotqueue = new LinkedTransferQueue<Patient>(); //Queue for patients awaiting a shot
	private static ArrayList<Patient> lecture = new ArrayList<Patient>(Main.getTotalPatients()); //List for patients waiting for lecture
	private static  LinkedTransferQueue<Assistants> Aqueue = new LinkedTransferQueue<Assistants>(); //Queue for an available Assistant
	private static Thread doctor;
	private static final long startTime = System.currentTimeMillis();
	private static boolean remainingPatients = true;
	
	
	public Doctor(String string) {
		doctor = new Thread(this, string);
		doctor.start();
	}

	
	@Override
	public void run() { //Have count for NUM_P total has been met or assitant notified
		while(remainingPatients){
			sleep(); //Sleep unless interrupted
		giveLecture(); //Once all patients arrive give lecture
		}
		System.out.println(age()+ ": " + doctor.getName() + " went home");
	}

	public void sleep() {
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			Assistants assist = getAssistant();
			if (giveShot()) { //Randomly decide to give shot
				 //Grab an assistant to give shot
				assist.GiveShot(true); 
			}else{
				assist.GiveShot(false);	
			}
			}
		
		
	}
	
	public void giveLecture(){
			if(Main.getpatientCounter() == Main.getTotalPatients() && lecture.isEmpty() !=true){ //Check if all patients were created and there's patients waiting for lecture
				try {
					System.out.println(age()+ ": " +doctor.getName() + " is giving lecture");
					Thread.sleep(8000);
				} catch (InterruptedException e1) {}
				
				for (int i = 0; i < Main.returnPatient().length; i++) {	//Give lecture to patients that are waiting
				if(Main.returnPatient()[i].getWaitingLecture() == true){
					System.out.println(age()+ ": "+ Main.returnPatient()[i].getName() + " got lecture and went home");
					Main.returnPatient()[i].setWaitingLecture(false);;
					Main.returnPatient()[i].setWaitingDoctor(false);;
				}
				Main.returnPatient()[i].Join();
				
			}
					for (int i = 0; i < Main.returnAssistant().length; i++) { //Have assistants go home
						Main.returnAssistant()[i].setRemainingPatients(false);
					}
					remainingPatients = false; //Have doctor go home
			}
	}
	
	public  boolean giveShot(){ //Randomly decide to give patient a shot
		if(new Random().nextInt(2) == 1){
			return true;
		}else
			return false;
	}
	
	public static void addShotQueue(Patient patient) { //Assistant puts patient in waiting room
		Pshotqueue.add(patient);
	}
	public static synchronized void addToPatientLecture(Patient patient)  { //If patient got shot put in room for lecture
		lecture.add(patient);
	}
	
	public static  Patient getShotQueue(){ //return the patients who needs a shot
		Patient patient = null;
		try {
			patient = Pshotqueue.take();
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
		return patient;
	}
	
	
	public static synchronized void  interruptDoctor(){ //interrupt the doc to decide to give shot
		doctor.interrupt();
		
	}

	public static void addToAssistantQueue(Assistants assist){ //assistant tells doc it's free
		Aqueue.add(assist);
	}
	public static void ClearAssistant(){ //assistant tells doc it's free
		Aqueue.clear();;
	}
	
	public static Assistants getAssistant(){ //doc grabs an available assistant
		Assistants assist = null;
		try {
			assist = Aqueue.take();
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
		return assist;
	}
	protected final long age() { // Returns the age of the thread
		return System.currentTimeMillis() - startTime;
	}

}