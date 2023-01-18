package model;

import allExceptions.NameException;

public class Player {
    private static int idGenerator = 1;
    
	private String name;
	private int id;
	private boolean playerStatus;
	
	public Player(String name) throws NameException {
		setName(name);
		id = idGenerator;
		idGenerator++;
		playerStatus = true;
	}
	
	private void setName(String name) throws NameException {
		if (name.trim().isEmpty()) {
			throw new NameException();
		}
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean getStatus() {
		return playerStatus;
	}
	
	public void setStatus(Boolean playerStatus) {
		this.playerStatus = playerStatus;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Player)) {
			return false;
		}
		return ((Player)other).id == id;
	}


}
