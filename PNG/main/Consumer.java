package main;

import exceptions_handler.*;

public final class Consumer extends Pipeline implements Runnable {

	public Consumer(Queue _queue) {
		super(_queue);
	}
	

	private void consume() throws QueueFoundNumbersException {
		try {
			Number pnc = this.queue().read();
		} catch (QueueEmptyException e) {
			System.err.println(e.getMessage());

			Thread.yield();
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}catch (QueueFoundNumbersException qfne) {
			throw qfne;
		}
	}
	
	@Override
	public void run() {
		boolean work_permission = true;
		while(work_permission) {
			try {
				this.consume();
			} catch (QueueFoundNumbersException e) {
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
