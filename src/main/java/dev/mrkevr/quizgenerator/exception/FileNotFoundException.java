package dev.mrkevr.quizgenerator.exception;

public class FileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileNotFoundException(String id) {
		super("No file found with name/id " + id);
	}

	public FileNotFoundException() {
		super("No file found with that name/id");
	}
}
