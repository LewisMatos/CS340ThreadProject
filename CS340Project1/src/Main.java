/**
 * Lewis Matos
 * CS 340
 * PROJECT 1
 * Professor Fluture
 */

import java.util.concurrent.LinkedTransferQueue;

public final class Main { //In charge of creating the threads and keeping track of PatientQueues
	private static volatile int NUM_P;
	private static volatile int NUM_A;
	private static volatile int patientCount;
	private static Patient patient[];
	private static Assistants assistant[];
	private static Doctor doctor[];
	private static LinkedTransferQueue<Patient> queue = new LinkedTransferQueue<Patient>(); //List with built in synchronization

	public static void main(String[] args) {

		if (args.length != 2) { //Check if correct amount of arguments passed
			System.out.println("Didn't input the correct arguments");
		} else {
			NUM_P = Integer.parseInt(args[0]);
			NUM_A = Integer.parseInt(args[1]);
			createThreads();
		}
	}

	private static void createThreads() { //Creates all the threads,doctor, assistants and patients

		patient = new Patient[NUM_P];
		for (int i = 0; i < NUM_P; i++) {
			patient[i] = new Patient("Patient:" + (i + 1));

		}

		doctor = new Doctor[1];
		for (int i = 0; i < doctor.length; i++) {
			doctor[i] = new Doctor("Doctor");
		}

		assistant = new Assistants[NUM_A];
		for (int i = 0; i < NUM_A; i++) {
			assistant[i] = new Assistants("Assistant:" + (i + 1));
		}

		// remember to check to if patients are alive
		// Implement an array to check to see if patients are going home

	}

	public static void setPatientQueue(Patient pat) { //patients enter the queue when arriving to office
		queue.add(pat);
	}

	public static Patient getPatientQueue() { //Returns patients in fcfs who arrived in office
		Patient temp = null;
		try {
			temp = queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	public static synchronized int getTotalPatients() { //return the total amount of patients
		return NUM_P;
	}

	public static  void incrementpatientCounter() { //Patients increment counter when created
		patientCount++;
	}

	public static  int getpatientCounter() { //return the current amount of patients
		return patientCount;
	}

	public static synchronized boolean checkPatientQueue() { //Check to see if the Patient queue is empty
		return queue.isEmpty();
	}

	public static synchronized Assistants[] returnAssistant() { //returns the assistant array to be able to access to all avail assistants
		return assistant;
	}

	public static synchronized Patient[] returnPatient() { //returns the patient array to have access to all patients
		return patient;
	}
}
