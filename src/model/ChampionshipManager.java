package model;

import java.util.Vector;

import allExceptions.GameNotOverException;
import allExceptions.GameNotValidException;
import allExceptions.GameOverException;
import allExceptions.MissingParticipantsException;
import allExceptions.NameException;
import allExceptions.OtherGameActiveException;
import allExceptions.PlayerNotExistsException;
import allExceptions.ScoreException;
import allExceptions.TournamentFullException;
import listeners.ModelEventsListener;

public class ChampionshipManager {
	public static final int NUM_OF_PLAYERS = 8; 
	public enum game {TENNIS, BASKETBALL, SOCCER}
	
	private Vector<Player> players;
	private Championship<Tennis> tennisChampionship;
	private Championship<Soccer> soccerChampionship;
	private Championship<Basketball> basketballChampionship;
	private game gameType;
	private Vector<ModelEventsListener> listeners;
	private boolean isGameActive;

	public ChampionshipManager() {
		players = new Vector<Player>();
		listeners = new Vector<ModelEventsListener>();
		isGameActive = false;
	}
	
	public void registerModelListener(ModelEventsListener listener) {
		listeners.add(listener);
		listener.numOfPlayers(NUM_OF_PLAYERS);
	}
	
	public void addParticipant(String name) throws TournamentFullException, NameException {
		if (players.size() == NUM_OF_PLAYERS) {
			throw new TournamentFullException();
		}
		Player newPlayer = new Player(name);
		players.add(newPlayer);
		for (int i = 0; i < listeners.size(); i++) {
			listeners.elementAt(i).fireAddParticipantToModelEvent(newPlayer.getName(), newPlayer.getId());
		}
	}
	
	public void startChampionship(String gameType) throws MissingParticipantsException {
		this.gameType = game.valueOf(gameType);
		if (players.size() != NUM_OF_PLAYERS) {
			throw new MissingParticipantsException(NUM_OF_PLAYERS-players.size());
		}
		if (this.gameType.equals(game.TENNIS)) {
			tennisChampionship = new Championship<Tennis>();
		}
		else {
			if(this.gameType.equals(game.SOCCER)) {
				soccerChampionship = new Championship<Soccer>();
			}
			else {
				basketballChampionship = new Championship<Basketball>();
			}
		}
		
		for (int i = 0; i < listeners.size(); i++) {
			listeners.elementAt(i).fireStartChampionshipModelEvent(this.gameType);
		}
	}
	
	 public void setGame(int firstPlayerId, int secondPlayerId) throws PlayerNotExistsException, GameNotValidException, OtherGameActiveException {
		 Player firstPlayer = getPlayerById(firstPlayerId);
	     Player secondPlayer = getPlayerById(secondPlayerId);
	     if (!firstPlayer.getStatus() || !secondPlayer.getStatus()) {
	    		throw new GameNotValidException();
	     }
	     if (isGameActive) {
	    	 throw new OtherGameActiveException();
	     }
	     isGameActive = true;
	     if (gameType.equals(game.TENNIS)) {
	    	 addTennisGame(firstPlayer, secondPlayer);
		 }
		 else {
			 if(gameType.equals(game.SOCCER)) {
				 addSoccerGame(firstPlayer, secondPlayer);
			 }
			 else {
				 addBasketballGame(firstPlayer, secondPlayer);
			 }
		 }
	     for (int i = 0; i < listeners.size(); i++) {
				listeners.elementAt(i).fireSetGameInModelEvent(firstPlayer.getId(), secondPlayer.getId());
		 }
	 }
	 
	 private Player getPlayerById(int id) throws PlayerNotExistsException {
		 for (int i = 0; i < players.size(); i++) {
			 if (players.elementAt(i).getId() == id) {
				 return players.elementAt(i);
			 }
		 }
	     throw new PlayerNotExistsException();
	 }
	 
	 private void addTennisGame(Player firstPlayer, Player secondPlayer) {
		 tennisChampionship.addGame(new Tennis(firstPlayer, secondPlayer));
	 }
	 
	 private void addSoccerGame(Player firstPlayer, Player secondPlayer) {
		 soccerChampionship.addGame(new Soccer(firstPlayer, secondPlayer));
	 }
	 
	 private void addBasketballGame(Player firstPlayer, Player secondPlayer) {
		 basketballChampionship.addGame(new Basketball(firstPlayer, secondPlayer));
	 }
	 
	 public void addRoundScores(int firstPlayerId, int firstPlayerScore, int secondPlayerId, int secondPlayerScore) throws PlayerNotExistsException, GameOverException, ScoreException, GameNotOverException, GameNotValidException  {
		 if (gameType.equals(game.TENNIS)) {
			 updateGameResults(tennisChampionship, firstPlayerScore, secondPlayerScore, new Tennis (getPlayerById(firstPlayerId), getPlayerById(secondPlayerId)));
		 }
		 else {
			 if(gameType.equals(game.SOCCER)) {
				 updateGameResults(soccerChampionship, firstPlayerScore, secondPlayerScore, new Soccer (getPlayerById(firstPlayerId), getPlayerById(secondPlayerId)));
			 }
			 else {
				 updateGameResults(basketballChampionship, firstPlayerScore, secondPlayerScore, new Basketball (getPlayerById(firstPlayerId), getPlayerById(secondPlayerId)));
			 }
		 }
	 }
	 
	 private<Type extends Game> void updateGameResults(Championship<Type> championship, int firstPlayerScore, int secondPlayerScore, Type game) throws PlayerNotExistsException, GameOverException, ScoreException, GameNotValidException, GameNotOverException {
		 for (int i = 0; i < championship.getGames().size(); i++) {
			 if (game.equals(championship.getGames().elementAt(i))) {
				 game = championship.getGames().elementAt(i);
				 game.addRoundScores(firstPlayerScore, secondPlayerScore);
				 for (int j = 0; j < listeners.size(); j++) {
					 listeners.elementAt(j).fireRoundAddInModelEvent();
				 }
				 Player winner = game.getWinner();
				 isGameActive = false;
				 for (int j = 0; j < listeners.size(); j++) {
					 listeners.elementAt(j).fireGameOverInModelEvent(winner.getName(), winner.getId());
				 }
				 return;
			 }
		 }
		 throw new GameNotValidException();
	 }
}
