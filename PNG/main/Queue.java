package main;
import java.util.ArrayList;
import exceptions_handler.*;

public class Queue extends ArrayList<Number> {

	private static final long serialVersionUID = 1L; //unique identifier from ArrayList
	public static int WAIT_TIME = 20;
	private static int max_Pnum = 10000;
	private static int queue_Size = 100000;

	private int readCursor = 0; //consumer
	private int writeCursor = 0; //producer
	private int findingPrimeNumbers;
	private int primeNumbersFound = 0;
 
	private Object queueLock = new Object(); // queue lock to manage concurrent actions on queue

	private static Queue queue = null; //initialize empty queue

	private Queue(int findingPrimeNumbers) throws QueueSizeLimitException {
		if (!this.isValidNumberOfPrimeNumbers(findingPrimeNumbers)) {
			throw new QueueSizeLimitException("Queue Size does not Fit");
		}
		this.findingPrimeNumbers = findingPrimeNumbers;
	}

	//instance of the Queue class
	public static Queue getQueue(int findingPrimeNumbers) throws QueueSizeLimitException {
		if (Queue.queue == null)
			queue = new Queue(findingPrimeNumbers);
		return queue;
	}

	/*** GETTERS AND SETTERS ***/
	private int getReadCursor() {return readCursor;}
	private void incrementReadCursor() {this.readCursor++;}
	private int getWriteCursor() {return writeCursor;}
	private void incrementWriteCursor() {this.writeCursor++;}
	private int findingPrimeNumbers() {return findingPrimeNumbers;}
	private int getPrimeNumbersFound() {return primeNumbersFound;}
	private void incrementPrimeNumbersFound() {this.primeNumbersFound++;}


	//Checks prime numbers not greater than max_Pnum
	private boolean isValidNumberOfPrimeNumbers(int primeNumbersWanted) {
		return primeNumbersWanted < Queue.max_Pnum;
	}

	 //last item inserted has been read or not
	private boolean isReadable() {
		return this.getWriteCursor() > this.getReadCursor();
	}

	 //Queue is full or not. new item can be inserted into the Queue?
	private boolean isWritable() {
		return this.size() < queue_Size;
	}

	//all the prime numbers has been found
	private boolean primeNumbersFound() {
		return this.getPrimeNumbersFound() >= this.findingPrimeNumbers();
	}

	//next item to read in the queue
	protected Number read() throws QueueEmptyException, QueueFoundNumbersException {

		synchronized (this.queueLock) { // critical section in synchronization
			if (this.primeNumbersFound()) {
				throw new QueueFoundNumbersException("QueueFoundNumbersException");
			}

			if (!this.isReadable()) {
				throw new QueueEmptyException("Queue is empty");
			}
			Number number = super.get(this.getReadCursor());
			this.incrementReadCursor();

			if (number.isPrime()) {
				number.setPrime(true);
				this.incrementPrimeNumbersFound();
			}
			return number;
		}
	}

	//new Number into the queue
	protected void write() throws QueueFullException, QueueFoundNumbersException {
		synchronized (this.queueLock) { // critical section in synchronization
			if (this.primeNumbersFound()) {
				throw new QueueFoundNumbersException("QueueFoundNumbersException");
			}
			if (!this.isWritable()) {
				throw new QueueFullException("Queue is full");
			}
			Number number = new Number(this.getWriteCursor());
			this.add(number);
			this.incrementWriteCursor();
		}
	}
}
