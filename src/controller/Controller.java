package controller;

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
import listeners.UIEventsListener;
import model.ChampionshipManager;
import view.AbstractView;

public class Controller implements ModelEventsListener, UIEventsListener {
	
	private ChampionshipManager model;
    private AbstractView view;
	
    public Controller(AbstractView view, ChampionshipManager model) {
    	this.view  = view;
    	this.model = model;
        model.registerModelListener(this);
        view.registerUIListener(this);
    }
    
	@Override
	public void addParticipantInUI(String name) {
		try {
			model.addParticipant(name);
		}
		catch (TournamentFullException exception) {
			view.tournamentFullMessage(exception.getMessage());
		}
		catch(NameException exception) {
			view.nameEmptyMessage(exception.getMessage());
		}
	}

	@Override
	public void fireAddParticipantToModelEvent(String name, int id) {
		view.addParticipantToUI(name, id);
	}

	@Override
	public void startChampionshipInUI(AbstractView.game gameType) {
		try {
			model.startChampionship(gameType.name()); 
		}
		catch(MissingParticipantsException exception) {
			view.missingParticipantsMessage(exception.getMessage());
		}
	}
	
	@Override
	public void fireStartChampionshipModelEvent(ChampionshipManager.game gameType) {
		view.startChampionshipInUI((AbstractView.game.valueOf(gameType.name())));
	}
	
	@Override
	public void setGameInUI(int firstPlayerId, int secondPlayerId) {
		try {
			model.setGame(firstPlayerId, secondPlayerId);
		}
		catch(PlayerNotExistsException exception) {
			view.playerNotExistsMessage(exception.getMessage());
		}
		catch(GameNotValidException exception) {
			view.gameNotValidMessage(exception.getMessage());
		}
		catch(OtherGameActiveException exception) {
			view.otherGameActiveMessage(exception.getMessage());
		}
	}

	@Override
	public void fireSetGameInModelEvent(int firstPlayerId, int SecondPlayerId) {
		view.setGameToUI(firstPlayerId, SecondPlayerId);
	}
	
	@Override
	public void roundOverInUI(int firstPlayerId, int firstPlayerScore, int secondPlayerId, int secondPlayerScore) {
		try {
			model.addRoundScores(firstPlayerId, firstPlayerScore, secondPlayerId, secondPlayerScore);
		}
		catch(ScoreException exception) {
			view.scoreNotValidMessage(exception.getMessage());
		}
		catch(PlayerNotExistsException exception) {
			view.playerNotExistsMessage(exception.getMessage());
		}
		catch(GameOverException exception) {
			view.gameOverMessage(exception.getMessage());
		}
		catch(GameNotValidException exception) {
			view.gameNotValidMessage(exception.getMessage());
		}
		catch(GameNotOverException exception) {
			view.gameNotOverMessage(exception.getMessage());
		}
	}
	
	@Override
	public void fireRoundAddInModelEvent() {
		view.addRoundToUI();
	}

	@Override
	public void fireGameOverInModelEvent(String winnerName, int winnerId) {
		view.gameOverToUI(winnerName, winnerId);
	}
	
	
	@Override
	public void numOfPlayers(int numOfPlayers) {
		view.sendNumOfPlayersToUI(numOfPlayers);
	}

	
	
}
