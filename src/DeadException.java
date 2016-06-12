
public class DeadException extends Exception {
	private static final long serialVersionUID = 1L;
	public DeadException() { super(); }
	public DeadException(String message) { super(message); }
	public DeadException(String message, Throwable cause) { super(message, cause); }
	public DeadException(Throwable cause) { super(cause); }
}