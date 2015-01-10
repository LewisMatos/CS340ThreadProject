/**
 * Lewis Matos
 * CS 340
 * PROJECT 1
 * Professor Fluture
 */
import java.util.Random;

public class Assistants implements Runnable {
	private Thread Athread;
	private boolean giveshot = false;
	private static final long startTime = System.currentTimeMillis();
	private boolean remainingPatients = true;

	public Assistants(String string) {
		Athread = new Thread(this, string);
		Athread.start();
	}

	@Override
	public void run() {

		while (remainingPatients) { // While queue is not empty and the number
									// of patients have not been met. Grab patients
			GrabPatient(); //Grab a waiting patient
		}
		System.out.println(age()+": "+ getName() + " went home");
	}

	public void GrabPatient() { //Grabs patients take information and then put patient in waiting room until doc decides to give shot
		
		if (Main.checkPatientQueue() != true) {
		
			Patient temp = Main.getPatientQueue(); // Check to see if any patients
											// arrived at the office
			temp.setWaitingAssistance(false);
			sleep(); // Take patients information
			System.out
					.println(age() + ": " + temp.getName()
							+ " Walked into the office and is having its information being taken by "
							+ getName());
			
			Doctor.addShotQueue(temp); //Add patient to get shot
			Main.incrementpatientCounter(); 
	//		Doctor.addToAssistantQueue(this); //Assistant is now free to give shot
			Doctor.interruptDoctor(); //Interrupt the doctor to decide if need shot
		} else {
			Doctor.ClearAssistant();
		}
		Doctor.addToAssistantQueue(this);
	}

	public void GiveShot(boolean gs) { //Doctor decides if patient needs a shot
		giveshot = gs;
		if (giveshot == true) { 

			setPriority(Athread.getPriority() + 2);
			Patient pshot = Doctor.getShotQueue();
			pshot.setWaitingDoctor(false);
			Doctor.addToPatientLecture(pshot);
			System.out.println(age() +": " + pshot.getName() + " got a shot by  " + getName());
		

		} else {
			
			Patient pnotshot = Doctor.getShotQueue();
			pnotshot.setWaitingDoctor(false);
			pnotshot.setWaitingLecture(false);
			System.out.println(age() +": " + pnotshot.getName() + " didnt' get a shot by  " + getName());
	
			// pnotshot.Interrupt();
		}

	}

	public void sleep() { // Assistant will take customers information for a//
							// Certain amount of time
		try {
			Thread.sleep(new Random().nextInt(4000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setRemainingPatients(boolean remain) { //Assistant when doc gives lecture and leaves
		remainingPatients = remain;
	}

	public void setPriority(int priority) { //Increase the Assistant priority
		long startTime = System.currentTimeMillis();
		long endTime = startTime + (10);

		while (System.currentTimeMillis() < endTime) {
			Athread.setPriority(priority);
		}
		Athread.setPriority(Thread.NORM_PRIORITY);

	}

	public int getPriority() { //Get the assitants current priority
		return Athread.getPriority();
	}

	public String getName() { // Get the Current Assistants Name
		return Athread.getName();
	}

	protected final long age() { // Returns the age of the thread
		return System.currentTimeMillis() - startTime;
	}

}
