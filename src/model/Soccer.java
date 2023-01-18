package model;

import allExceptions.GameOverException;
import allExceptions.ScoreException;

public class Soccer extends Game {
	public static final int MIN_ROUNDS_FOR_SOCCER_GAME = 2; 
	public static final int EXTRA = 3; 
	
	public Soccer(Player firstPlayer, Player secondPlayer) {
		super(firstPlayer, secondPlayer);
	}
	
	@Override
	public void addRoundScores(int firstPlayerScore, int secondPlayerScore) throws GameOverException, ScoreException {
		super.addRoundScores(firstPlayerScore, secondPlayerScore);
		boolean isTie = (firstPlayerTotalScore == secondPlayerTotalScore);
		if(!(numOfRounds < MIN_ROUNDS_FOR_SOCCER_GAME || isTie && (numOfRounds == MIN_ROUNDS_FOR_SOCCER_GAME || numOfRounds == EXTRA))) {
			setWinner();
		}
	}
	
	@Override 
	public boolean equals(Object other) {
		if (!(other instanceof Soccer)) {
			return false;
		}
		return super.equals(other);
	}

}
