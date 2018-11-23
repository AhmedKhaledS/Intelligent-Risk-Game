package game.state;

import game.world.Country;

public class ArmyPlacement implements Cloneable {

	private Country chosenCountry;
	private int troopsCount;
	
	public ArmyPlacement(Country chosenCountry, int troopsCount) {
		this.chosenCountry = chosenCountry;
		this.troopsCount = troopsCount;
	}
	
	
	public Country getChosenCountry() {
		return chosenCountry;
	}
	public void setChosenCountry(Country chosenCountry) {
		this.chosenCountry = chosenCountry;
	}
	public int getTroopsCount() {
		return troopsCount;
	}
	public void setTroopsCount(int troopsCount) {
		this.troopsCount = troopsCount;
	}
	
	public ArmyPlacement clone() {
		ArmyPlacement clonedArmyPlacement = new ArmyPlacement(chosenCountry.clone(), troopsCount);
		return clonedArmyPlacement;
	}
}
