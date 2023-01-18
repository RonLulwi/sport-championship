package model;

import allExceptions.GameNotOverException;
import allExceptions.GameOverException;
import allExceptions.ScoreException;

public abstract class Game {
	protected Player firstPlayer;
	protected Player secondPlayer;
	protected int numOfRounds;
	protected int firstPlayerTotalScore;
	protected int secondPlayerTotalScore;
	protected boolean isGameOver;
	
	public Game(Player firstPlayer, Player secondPlayer) {
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		numOfRounds = 0;
		firstPlayerTotalScore = 0;
		secondPlayerTotalScore = 0;
		isGameOver = false;
	}
	
	public void addRoundScores(int firstPlayerScore, int secondPlayerScore) throws GameOverException, ScoreException {
		if (isGameOver) {
			throw new GameOverException();
		}
		if(firstPlayerScore < 0 || secondPlayerScore < 0) {
			throw new ScoreException() ;
		}
		if (this instanceof Tennis) {
			if (firstPlayerScore > secondPlayerScore) {
				firstPlayerTotalScore++;
			}
			else {
				if (secondPlayerScore > firstPlayerScore) {
					secondPlayerTotalScore++;
				}
			}
		}
		else {
			firstPlayerTotalScore = firstPlayerTotalScore + firstPlayerScore;
			secondPlayerTotalScore = secondPlayerTotalScore + secondPlayerScore;
		}
		numOfRounds++;
	}
	
	public void setWinner()  {
		isGameOver = true;
		if (firstPlayerTotalScore > secondPlayerTotalScore) {
			firstPlayer.setStatus(true);
			secondPlayer.setStatus(false);
		}
		else {
			firstPlayer.setStatus(false);
			secondPlayer.setStatus(true);
		}
	}
	
	public Player getWinner() throws GameNotOverException {
		if (!isGameOver) {
			throw new GameNotOverException();
		}
		if (firstPlayerTotalScore > secondPlayerTotalScore) {
			return firstPlayer;
		}
		return secondPlayer;
	}
	
	@Override
	public boolean equals(Object other) {
		Game otherGame = (Game)other;
		if (firstPlayer.equals(otherGame.firstPlayer) && secondPlayer.equals(otherGame.secondPlayer)){
			return true;
		}
		if (firstPlayer.equals(otherGame.secondPlayer) && secondPlayer.equals(otherGame.firstPlayer)){
			return true;
		}
		return false;
	}
	
	
	
}