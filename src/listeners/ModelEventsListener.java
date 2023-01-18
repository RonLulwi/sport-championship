package listeners;

import model.ChampionshipManager;

public interface ModelEventsListener {
	
	void fireAddParticipantToModelEvent(String name, int id);
	void fireStartChampionshipModelEvent(ChampionshipManager.game gameType);
	void fireSetGameInModelEvent(int firstPlayerId, int SecondPlayerId);
	void fireGameOverInModelEvent(String name, int id);
	void numOfPlayers(int numOfPlayers);
	void fireRoundAddInModelEvent();
	
}
