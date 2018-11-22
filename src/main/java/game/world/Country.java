package game.world;

import game.Player;

public class Country {
	
	private Player owner;
	private int armiesSize;
	private int index;
	
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getArmiesSize() {
		return armiesSize;
	}
	public void setArmiesSize(int armiesSize) {
		this.armiesSize = armiesSize;
	}

}
