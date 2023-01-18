package allExceptions;

public class OtherGameActiveException extends Exception {
	
	public OtherGameActiveException() {
		super("Other game is already active");
	}

}
