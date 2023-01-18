package allExceptions;

public class ScoreException extends Exception {

	public ScoreException() {
		super("Score must be a positive integer");
	}

}
