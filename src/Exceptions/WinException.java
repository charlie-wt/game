package Exceptions;
public class WinException extends Exception {
	private static final long serialVersionUID = 1L;
	public WinException() { super(); }
	public WinException(String message) { super(message); }
	public WinException(String message, Throwable cause) { super(message, cause); }
	public WinException(Throwable cause) { super(cause); }
}