package allExceptions;

public class GameNotValidException extends Exception {
	
	public GameNotValidException() {
		super("The requested game cannot be held");
	}

}
