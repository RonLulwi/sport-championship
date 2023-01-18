package listeners;

import view.AbstractView.game;

public interface UIEventsListener {
	
	void addParticipantInUI(String name);
	void startChampionshipInUI(game gameType);
	void setGameInUI(int firstPlayerId, int secondPlayerId);
	void roundOverInUI(int firstPlayerId, int firstPlayerScore, int secondPlayerId, int secondPlayerScore);
}
