package exceptions_handler;
/*
 * Queue is empty, nothing can be read from it
 * In other terms, this exception is thrown when the queue's readCursor equals the queue's writeCursor
 */
public class QueueEmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2889327413278340237L;

	public QueueEmptyException() {
		// TODO Auto-generated constructor stub
	}

	public QueueEmptyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public QueueEmptyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public QueueEmptyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public QueueEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
