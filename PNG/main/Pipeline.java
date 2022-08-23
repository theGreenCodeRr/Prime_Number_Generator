package main;

public abstract class Pipeline { //data piping to consumer/producer from queue  

	private Queue queue;

	protected Pipeline(Queue queue) {this.setQueue(queue);}
	protected Queue queue() {return queue;}
	private void setQueue(Queue queue) {this.queue = queue;}
}
