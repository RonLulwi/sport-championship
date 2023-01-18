package model;

import allExceptions.GameOverException;
import allExceptions.ScoreException;

public class Tennis extends Game {
	public static final int MAX_ROUNDS_FOR_TENNIS_GAME = 5 ; 
	public static final int WIN_DIFF = 3 ; 
	
	public Tennis(Player firstPlayer, Player secondPlayer) {
		super(firstPlayer, secondPlayer);
	}
	
	@Override
	public void addRoundScores(int firstPlayerScore, int secondPlayerScore) throws GameOverException, ScoreException {
		super.addRoundScores(firstPlayerScore, secondPlayerScore);
		if (numOfRounds == MAX_ROUNDS_FOR_TENNIS_GAME || Math.abs(firstPlayerTotalScore - secondPlayerTotalScore) == WIN_DIFF) {
			setWinner();
		}
	}
	
	@Override 
	public boolean equals(Object other) {
		if (!(other instanceof Tennis)) {
			return false;
		}
		return super.equals(other);
	}


}
