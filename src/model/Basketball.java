package model;

import allExceptions.GameOverException;
import allExceptions.ScoreException;

public class Basketball extends Game {
	
	public static final int BASKETBALL_QUARTERS = 4 ; 
	
	public Basketball(Player firstPlayer, Player secondPlayer) {
		super(firstPlayer, secondPlayer);
	}
	
	@Override
	public void addRoundScores(int firstPlayerScore, int secondPlayerScore) throws GameOverException, ScoreException   {
		super.addRoundScores(firstPlayerScore, secondPlayerScore);
		if (numOfRounds == BASKETBALL_QUARTERS) {
			setWinner();
		}
	}
	
	@Override 
	public boolean equals(Object other) {
		if (!(other instanceof Basketball)) {
			return false;
		}
		return super.equals(other);
	}

}
