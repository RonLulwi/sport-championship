package allExceptions;

public class MissingParticipantsException extends Exception {
	
	public MissingParticipantsException(int numOfMissingParticipants) {
		super("Can not start the championship, please enter " + numOfMissingParticipants + " more participants");
	}

}
