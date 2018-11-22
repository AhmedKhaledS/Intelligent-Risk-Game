package game.world;

import agents.Agent;

public class Country {
	
	private Agent owner;
	private int armiesSize;
	
	public Agent getOwner() {
		return owner;
	}
	public void setOwner(Agent owner) {
		this.owner = owner;
	}
	public int getArmiesSize() {
		return armiesSize;
	}
	public void setArmiesSize(int armiesSize) {
		this.armiesSize = armiesSize;
	}

}
