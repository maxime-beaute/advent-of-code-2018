package fr.insee.advent.exception;

public class AdventException extends Exception {

	private static final long serialVersionUID = -3531063630264317253L;

	public AdventException(String message) {
		super(message);
	}

	public AdventException(String message, Throwable cause) {
		super(message, cause);
	}
}
