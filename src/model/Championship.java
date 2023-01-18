package model;

import java.util.Vector;

public class Championship<Type extends Game> {
	
	private Vector<Type> games;
	
	public Championship() {
		games = new Vector<Type>();
	}
	
	public void addGame(Type newGame) {
		games.add(newGame);
	}
	
	public Vector<Type> getGames() {
		return games;
	}
	
}
