package allExceptions;

public class GameNotOverException extends Exception {
	
	public GameNotOverException() {
		super("Game not over yet, please enter another round");
	}

}
