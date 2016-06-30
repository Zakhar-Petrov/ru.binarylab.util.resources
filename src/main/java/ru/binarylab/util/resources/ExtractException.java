package ru.binarylab.util.resources;

@SuppressWarnings("serial")
public class ExtractException extends Exception {

	ExtractException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExtractException(String message) {
		super(message);
	}

}
