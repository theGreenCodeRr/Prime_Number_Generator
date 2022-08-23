package main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import exceptions_handler.*;

public class Main {
	
	//static variables
	public static int max_Pnum;
	public static int CONSUMERS = 100;
	public static int PRODUCERS = 150;
	

	public static String fileName = "Output_File.txt";

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);  
	       System.out.print("Enter a bigger Number: ");  
	       max_Pnum = s.nextInt();
	       s.close();
	       
	    Main.writeToFile("", false, Main.fileName); //clear file  
		
		long start = System.currentTimeMillis(); //time start here

		// List of Threads
		List<Thread> producerThreads = new ArrayList<Thread>(PRODUCERS);
		List<Thread> consumerThreads = new ArrayList<Thread>(CONSUMERS);

		Queue queue; //Queue data structure to hold the prime numbers

		try {
			queue = Queue.getQueue(max_Pnum);

			int i;

			Producer produce;
			Consumer consume;

			Thread producerThread;
			Thread consumerThread;

							/**** Begin THREAD Operations ****/
			
			i = 0; // initialize Producer
			while (i < PRODUCERS) { 
				produce = new Producer(queue);
				producerThread = new Thread(produce);
				producerThreads.add(producerThread);
				i++;
			}

			i = 0; // initialize Consumer
			while (i < CONSUMERS) {
				consume = new Consumer(queue);
				consumerThread = new Thread(consume);
				consumerThreads.add(consumerThread);
				i++;
			}
			
			i = 0;
			while (i < PRODUCERS) {
				producerThreads.get(i).start(); //all Producer Threads Started
				i++;
			}
			
			i = 0;
			while (i < CONSUMERS) {
				consumerThreads.get(i).start(); //all Consumer Threads Started
				i++;
			}
			
			i = 0; //stop all Producer Threads 
			while (i < PRODUCERS) {
				Thread pt = producerThreads.get(i);
				try {
					pt.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pt = null;
				i++;
			}
			
			i = 0;//stop all Consumer Threads 
			while (i < CONSUMERS) {
				Thread ct = consumerThreads.get(i);
				try {
					ct.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ct = null;
				i++;
			}
									/**** END of THREAD Operations ****/
			
			// Print queue to file
			Main.writeToFile(queue, true, Main.fileName);
			
		} catch (QueueSizeLimitException e) {
			System.err.println("Error: " + e.getMessage());
		}
		queue = null;
		
		// Print execution time to file
		System.out.println(Main.elapsedTime(start));
		Main.writeToFile(elapsedTime(start), true, Main.fileName);
	}

	
	public static String elapsedTime(long start) {
		long end = System.currentTimeMillis();
		float timeElapsed = (end - start) / 1000f;
		return "Execution time : " + timeElapsed + " seconds."; //return execution time in a string 
	}

	public static boolean writeToFile(String _text, boolean _append, String filename) {
		BufferedWriter out = null;
		boolean IsfileAppended = true;
		try {
			FileWriter fstream = new FileWriter(filename, _append);
			out = new BufferedWriter(fstream);
			if (_append)
				_text += "\n";
			out.write(_text);
		} catch (IOException e) {
			IsfileAppended = false;
			System.err.println("Error: " + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					IsfileAppended = false;
					e.printStackTrace();
				}
			}
		}
		return IsfileAppended;
	}

	public static boolean writeToFile(Queue _queue, boolean _append, String filename) {
		BufferedWriter out = null;
		boolean IsfileAppended = true;
		String lf = null;
		try {
			FileWriter fstream = new FileWriter(filename, _append);
			out = new BufferedWriter(fstream);
			if (_append)
				lf = "\n";
			for (int i = 0; i < _queue.size(); i++) {
				if (_queue.get(i).getPrime()) {
					System.out.println(_queue.get(i).getValue().toString());
					out.write(_queue.get(i).getValue().toString() + lf);
				}

			}
		} catch (IOException e) {
			IsfileAppended = false;
			System.err.println("Error: " + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					IsfileAppended = false;
					e.printStackTrace();
				}
			}
		}
		return IsfileAppended;
	}
}
