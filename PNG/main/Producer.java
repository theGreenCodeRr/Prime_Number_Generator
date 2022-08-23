package main;

import exceptions_handler.*;

public final class Producer extends Pipeline implements Runnable {

	public Producer(Queue _queue) {
		super(_queue);
	}

	private void produce() throws QueueFoundNumbersException, QueueFullException {
		
		try {
			// adding new prime number to queue
			this.queue().write();
			
		} catch (QueueFullException e) {
			System.err.println(e.getMessage());
			
			Thread.yield();
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			throw e;
		} catch (QueueFoundNumbersException qfne) {
			throw qfne;
		}
	}
	
	@Override
	public void run() {
		boolean work_permission = true;
		while(work_permission) {
			
			try {
				this.produce();
			} catch (QueueFoundNumbersException | QueueFullException e) {
				work_permission = false;
			}
			
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException e) {
				work_permission = false;
			}
		}
	}
	
}
