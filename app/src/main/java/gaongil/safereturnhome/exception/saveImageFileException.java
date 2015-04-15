package gaongil.safereturnhome.exception;

@SuppressWarnings("serial")
public class saveImageFileException extends Exception {
	
	public saveImageFileException(String errorMessage) {
		super(errorMessage);
	}
	
	public saveImageFileException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
