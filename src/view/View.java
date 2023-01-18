package view;

import java.util.Vector;

import javax.swing.JOptionPane;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import listeners.UIEventsListener;

public class View implements AbstractView {

	private Vector<UIEventsListener> allListeners;
	
	private Stage primaryStage;
	private HBox hbFirstLayout;
	private GridPane gpSecondLayout;
	private StackPane spAllLayouts;
	private Stage gameStage;
	
	private Vector<Label> lblPlayers;
	private int numOflblPlayers;
	private GridPane gpPreliminariesPlayers;
	private int numOfPlayers;
	private Button[] btPlayGame;
	
	private VBox vbGame;
	private Label lblFirstPlayer; 
	private Label lblSecondPlayer;
	private GridPane gpResults;
	private Vector<TextField>[] txtResults;
	private int nextRoundNum;
	
	public View (Stage primaryStage) {
		
	}
	
	private void setView() {
		allListeners = new Vector<UIEventsListener>();
		setPlayers();
		setFirstLyout();
		setSecondLayout();
		setAllLayouts();
		setStage(primaryStage);
		setGameStage();
	}
	
	@Override
	public void registerUIListener(UIEventsListener newListener) {
		allListeners.add(newListener);
	}
	
	@Override
	public void sendNumOfPlayersToUI(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
		setView();
	}

	private void setStage(Stage primaryStage) {
		this.primaryStage = new Stage();
		this.primaryStage.setTitle("CHAMPIONSHIP");
		this.primaryStage.setScene(new Scene(spAllLayouts, 1380, 650));
		this.primaryStage.show();
	}
	
	private void setGameStage() {
		vbGame = setVBox();
		gameStage = new Stage();
		gameStage.setScene(new Scene(vbGame, 650, 300));
	}
	
	private void setAllLayouts() {
		spAllLayouts = new StackPane();
		spAllLayouts.getChildren().addAll(gpPreliminariesPlayers, hbFirstLayout, gpSecondLayout);
	}
	
	private void setPlayers() {
		gpPreliminariesPlayers = setGridPane(45, 40);
		lblPlayers = new Vector<Label>();
		numOflblPlayers = 0;
		for (int i = 0; i < numOfPlayers*2 - 1 ; i++) { 
			lblPlayers.add(setPlayerLabel(""));
			if (i < numOfPlayers) {
				gpPreliminariesPlayers.add(lblPlayers.elementAt(i), 1, i+1);   
			}
		}
		gpPreliminariesPlayers.setAlignment(Pos.CENTER_LEFT);
	}
	
	private void setFirstLyout() {
		hbFirstLayout = setHBox();
		VBox vbCenter = setVBox(); 
		HBox hbUpper = setHBox();
		HBox hbBottom = setHBox();
		Label lblPlayerName = new Label("Participant name: ");
		TextField txtPlayerName = new TextField();
		Button btAddPlayer = new Button("Add participent");
		Button btStartChampionship = new Button("Start championship");
		hbUpper.getChildren().addAll(lblPlayerName, txtPlayerName);
		hbBottom.getChildren().addAll(btAddPlayer, btStartChampionship);
		vbCenter.getChildren().addAll(hbUpper ,hbBottom);
		vbCenter.setAlignment(Pos.CENTER);
		
		VBox vbRight = setVBox(); 
		ToggleGroup tgGames = new ToggleGroup();
		RadioButton rbTennis = setRadioButton(game.TENNIS, tgGames);
		RadioButton rbSoccer = setRadioButton(game.SOCCER, tgGames);
		RadioButton rbBasketball = setRadioButton(game.BASKETBALL, tgGames);
		rbTennis.setSelected(true); 
		vbRight.getChildren().addAll(rbTennis, rbSoccer, rbBasketball);
		vbRight.setAlignment(Pos.CENTER_LEFT);
		
		hbFirstLayout.getChildren().addAll(vbCenter, vbRight);
		hbFirstLayout.setAlignment(Pos.CENTER);
		
		btAddPlayer.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
			public void handle(ActionEvent ae) {
	    		String name = txtPlayerName.getText();
	    		for (int i = 0; i < allListeners.size(); i++) {
    		    	allListeners.elementAt(i).addParticipantInUI(name);
    		    }  
	    		txtPlayerName.setText("");
	    	}
		});
		
		btStartChampionship.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
			public void handle(ActionEvent ae) {
	    		AbstractView.game gameType;
	    		if (rbSoccer.isSelected()) {
	    			gameType = AbstractView.game.SOCCER;
				}
	    		else {
	    			if (rbTennis.isSelected()) {
	    				gameType = AbstractView.game.TENNIS;
					}
	    			else {
	    				gameType = AbstractView.game.BASKETBALL;
	    			}
	    		}
	    		for (int i = 0; i < allListeners.size(); i++) {
    		    	allListeners.elementAt(i).startChampionshipInUI(gameType);
    		    }  
	    	}
		});
	}
	
	@Override
	public void addParticipantToUI(String name, int id) {
		lblPlayers.elementAt(numOflblPlayers).setText(name + " (" + id + ")");
		numOflblPlayers++;
	}

	@Override
	public void startChampionshipInUI(AbstractView.game gameType) {
		setGame(gameType); 
		hbFirstLayout.setVisible(false);
		gpSecondLayout.setVisible(true);
	}
	
	private void setSecondLayout() {
		gpSecondLayout = setGridPane(45, 40);
		gpSecondLayout.setVisible(false);
		btPlayGame = new Button[numOfPlayers-1]; 
		for (int i = 0; i < btPlayGame.length  ; i++) {
			btPlayGame[i] = new Button("Play a game");
			playGamePressed(i);
		}
		int rowGap = 2;
		int column = 1;
		int row;
		int arrayIndex = 0;	
		int numOfGamesForColumn = numOfPlayers / 2;
		for (int i = 1; i <= numOfPlayers / 2; i = i*2) {
			row = i;
			int lastIndex = arrayIndex + numOfGamesForColumn;
			while (arrayIndex < lastIndex) {
				gpSecondLayout.add(btPlayGame[arrayIndex], column, row);               
				gpSecondLayout.add(lblPlayers.elementAt(arrayIndex + numOfPlayers),  column + 1, row);  
				arrayIndex++;
				row = row + rowGap;
			}
			column = column + 2;
			rowGap = rowGap * 2;
			numOfGamesForColumn = numOfGamesForColumn / 2;
		}
		
	    gpSecondLayout.setAlignment(Pos.CENTER);
	}
	
	private void playGamePressed(int index) {
		btPlayGame[index].setOnAction(new EventHandler<ActionEvent>() {
		    	@Override
				public void handle(ActionEvent ae) {
		    		int firstPlayerId = getIdFromPlayerString(lblPlayers.elementAt(index*2).getText());
		    		int secondPlayerId = getIdFromPlayerString(lblPlayers.elementAt((index*2)+1).getText());
		    		for (int j = 0; j < allListeners.size(); j++) {
	    		    	allListeners.elementAt(j).setGameInUI(firstPlayerId, secondPlayerId);
	    		    } 
		    	}
			});
	}
	
	@Override
	public void setGameToUI(int firstPlayerId, int SecondPlayerId) {
		for (int i = 0; i < lblPlayers.size(); i++) {
			if (getIdFromPlayerString(lblPlayers.elementAt(i).getText()) == firstPlayerId) {
				lblFirstPlayer.setText(lblPlayers.elementAt(i).getText());
			}
			if (getIdFromPlayerString(lblPlayers.elementAt(i).getText()) == SecondPlayerId) {
				lblSecondPlayer.setText(lblPlayers.elementAt(i).getText());
			}
		}
		gameStage.show();
	}
	
	private void setGame(AbstractView.game game) {
		gpResults = setGridPane(20, 20);
		Label lblGameTitle = new Label(game.name());
		lblFirstPlayer = new Label(); 
		lblSecondPlayer = new Label();
		gpResults.add(lblFirstPlayer, 0, 0);
		gpResults.add(lblSecondPlayer, 0, 1);
		txtResults = (Vector<TextField>[]) new Vector[2];
		for (int i = 0; i < txtResults.length; i++) {
			txtResults[i] = new Vector<TextField>();
		}
		for (int i = 0; i < txtResults.length; i++) {
			for (int j = 0; j < 5; j++) {
				TextField txtResult = new TextField();
				txtResult.setMaxSize(50, 50);
				txtResult.setAlignment(Pos.CENTER);
				if (j != 0) {
					txtResult.setVisible(false);
				}
				txtResults[i].add(txtResult);
				gpResults.add(txtResults[i].elementAt(txtResults[i].size()-1), txtResults[i].size()+1, i);
			}
		}
		nextRoundNum = 0;
		
		Button btDone = new Button("Done");
		btDone.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
			public void handle(ActionEvent ae) {
	    		int firstPlayerScore = 0;
	    		int secondPlayerScore = 0;
	    		try {
    				firstPlayerScore = Integer.parseInt(txtResults[0].elementAt(nextRoundNum).getText());
    				secondPlayerScore = Integer.parseInt(txtResults[1].elementAt(nextRoundNum).getText());
    				for (int i = 0; i < allListeners.size(); i++) {
		    			allListeners.elementAt(i).roundOverInUI(getIdFromPlayerString(lblFirstPlayer.getText()), firstPlayerScore, getIdFromPlayerString(lblSecondPlayer.getText()), secondPlayerScore);
		    		}
    			}
    			catch (NumberFormatException exception) { 
					showErrorMessage("Score must be an integer");
				}
	    	}
		});
		
		vbGame.getChildren().addAll(lblGameTitle , gpResults , btDone );
		vbGame.setAlignment(Pos.CENTER);
	}
	
	@Override
	public void addRoundToUI() {
		for (int i = 0; i < txtResults.length; i++) {
			txtResults[i].elementAt(nextRoundNum).setDisable(true);
		}
		nextRoundNum++;
	}
	
	@Override
	public void gameOverToUI(String winnerName, int winnerId) {
		for (int i = 0; i < txtResults.length; i++) {
			for (int j = 0; j < txtResults[i].size(); j++) {
				txtResults[i].elementAt(j).setText("");
				txtResults[i].elementAt(j).setDisable(false);
				if (j != 0) {
					txtResults[i].elementAt(j).setVisible(false);
				}
			}
		}
		nextRoundNum = 0;
		gameStage.close();
		addParticipantToUI(winnerName, winnerId);
	}
	
	private void setRound() {
		for (int i = 0; i < txtResults.length; i++) {
			txtResults[i].elementAt(nextRoundNum).setVisible(true);
		}
	}
	
	private int getIdFromPlayerString(String playerString) {
		if (playerString.isEmpty()) {
			return 0;
		}
		String[] arr = playerString.split("[()]");
		return Integer.parseInt(arr[1]);
	}
	
	private Label setPlayerLabel(String name) {
		Label lblParticipant = new Label(name);
		lblParticipant.setMinSize(150, 30);
		lblParticipant.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		lblParticipant.setStyle("-fx-border-color: black;");
		lblParticipant.setAlignment(Pos.CENTER);
		return lblParticipant;
	}
	
	private RadioButton setRadioButton(game game, ToggleGroup tg) {
		RadioButton rb = new RadioButton(game.name());
		rb.setToggleGroup(tg);
		return rb;
	}
	
	private HBox setHBox() {
		HBox hBox = new HBox();
		hBox.setVisible(true);
		hBox.setSpacing(40);
		hBox.setPadding(new Insets(20));
		return hBox;
	}
	
	private VBox setVBox() {
		VBox vBox = new VBox();
		vBox.setVisible(true);
		vBox.setSpacing(30);
		vBox.setPadding(new Insets(20));
		return vBox;
	}
	
	private GridPane setGridPane(int vGap, int hGap) {
		GridPane gridPane = new GridPane();
		gridPane.setVgap(vGap);           
		gridPane.setHgap(hGap);
		return gridPane;
	}
	
	private void showErrorMessage(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}
	
	@Override
	public void tournamentFullMessage(String msg) {
		showErrorMessage(msg);
	}

	@Override
	public void nameEmptyMessage(String msg) {
		showErrorMessage(msg);
	}
	
	@Override
	public void gameOverMessage(String msg) {
		showErrorMessage(msg);
	}

	@Override
	public void scoreNotValidMessage(String msg) {
		showErrorMessage(msg);	
	}

	@Override
	public void gameNotOverMessage(String msg) {
		showErrorMessage("Please enter another round");
		setRound();	
	}
	
	@Override
	public void playerNotExistsMessage(String msg) {
		showErrorMessage(msg);
	}
	
	@Override
	public void missingParticipantsMessage(String msg) {
		showErrorMessage(msg);
	}

	@Override
	public void gameNotValidMessage(String msg) {
		showErrorMessage(msg);
	}

	@Override
	public void otherGameActiveMessage(String msg) {
		showErrorMessage(msg);
		gameStage.show();
	}
	
}

