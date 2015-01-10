/**
 * Lewis Matos
 * CS 340
 * PROJECT 1
 * Professor Fluture
 */
import java.util.Random;

public class Patient implements Runnable {
	private int NUM_CANDY = 0;
	private volatile int candy_threshold = 35;
	private Thread Pthread;
	private volatile boolean waitingforassitance = true;
	private volatile boolean waitingforDoctor = true;
	private volatile boolean waitingforlecture = true;
	private static final long startTime = System.currentTimeMillis();

	public Patient(String string) {
		Pthread = new Thread(this, string);
		Pthread.start();
	}

	
	@Override
	public void run() {
		System.out.println(age() +": " + getName()
				+ " is at a party and is eating candy");
		if (isSick()) { //randomly decide if patient becomes sick
			VisitDoctor();  //sleep on way to office
			WaitingForAssitance(); //busy wait for assitant
			WaitingForDoctor(); //busy wait for doctor to get shot
			WaitingforLecture(); //busy wait for lecture
		} else {
			Main.incrementpatientCounter(); //even though not sick increment counter
			GoHome();
		}
	}

	
	private void sleep() { // It randomly selects how long the patient will take to Doctors Office.
		try {
		
			Thread.sleep(new Random().nextInt(2000));
			System.out.println(age()+ ": " +getName() + " ate "
					+ NUM_CANDY
					+ " candies and became sick so it's visiting the doctor");
			
		} catch (InterruptedException e) {

		}
	}

	public void Join() {
		try {
			Pthread.join();
		} catch (InterruptedException e) {
		}
	}

	private void Randgen() { // Randomly generates how much candy the Patient has eaten.
		NUM_CANDY = new Random().nextInt(100);
	}

	private boolean isSick() { // Sets how much Candy can make a Patient sick
		Randgen();
		if (NUM_CANDY >= candy_threshold) {
			return true;
		} else
			return false;
	}

	public void WaitingForAssitance() { // BUSY WAITING for Assistant
		while (this.waitingforassitance == true) {
			// System.out.println(Thread.currentThread().getName() +  "is busy waiting");
		}
	}

	public void WaitingForDoctor() { // BUSY WAITING for Doc
		// free
		while (this.waitingforDoctor == true) {
			// System.out.println(Thread.currentThread().getName() +"is busy waiting");
		}
	}

	public void WaitingforLecture() { //BUSY WAITING for Lecture
		while (this.waitingforlecture == true) {
			// System.out.println(Thread.currentThread().getName() + "is busy waiting");
		}
	}

	public boolean getWaitingLecture() {
		return waitingforlecture;
	}

	private void VisitDoctor() { // Patient will sleep for random time on its way to the office
		sleep();
		Main.setPatientQueue(this);
	}

	private void GoHome() { //Patient didn't get sick so it heads home
		waitingforassitance = false;
		waitingforDoctor = false;
		waitingforlecture = false;
		System.out.println(age() + ": " + getName() + " ate "
				+ NUM_CANDY + " candies and is fine and went home "); 
	}

	public String getName() { // Get the Name of the Patient
		return Pthread.getName();
	}

	public void setWaitingAssistance(boolean waiting) { // Set if patient is waiting for assistant

		waitingforassitance = waiting;
	}

	public void setWaitingLecture(boolean waiting) { // Set if waiting for lecture
		waitingforlecture = waiting;
	}

	public void setWaitingDoctor(boolean waiting) { //Set if waiting for Doctor
		waitingforDoctor = waiting;

	}

	protected final long age() { // Returns the age of the thread
		return System.currentTimeMillis() - startTime;
	}

}
