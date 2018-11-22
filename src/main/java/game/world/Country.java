package game.world;

import game.Player;

public class Country {
	
	private Player owner;
	private int armiesSize;
	private int id;
	
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public int getIndex() {
		return id;
	}
	public void setIndex(int index) {
		this.id = index;
	}
	public int getArmiesSize() {
		return armiesSize;
	}
	public void setArmiesSize(int armiesSize) {
		this.armiesSize = armiesSize;
	}

}
