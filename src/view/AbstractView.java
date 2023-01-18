package view;

import listeners.UIEventsListener;

public interface AbstractView {
	
	public enum game {TENNIS, BASKETBALL , SOCCER } 
	
	void registerUIListener(UIEventsListener newListener);
	void addParticipantToUI(String name, int id);
	void tournamentFullMessage(String msg);
	void nameEmptyMessage(String msg);
	void missingParticipantsMessage(String message);
	void startChampionshipInUI(game gameType);
	void playerNotExistsMessage(String msg);
	void setGameToUI(int firstPlayerId, int secondPlayerId);
	void scoreNotValidMessage(String message);
	void gameOverMessage(String message);
	void gameNotOverMessage(String message);
	void gameNotValidMessage(String msg);
	void gameOverToUI(String winnerName, int winnerId);
	void sendNumOfPlayersToUI(int numOfPlayers);
	void addRoundToUI();
	void otherGameActiveMessage(String msg);
	
}
