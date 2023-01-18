package main;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ChampionshipManager;
import view.AbstractView;
import view.View;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		ChampionshipManager model = new ChampionshipManager();
		AbstractView view = new View(primaryStage);
		Controller controller = new Controller(view, model);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
