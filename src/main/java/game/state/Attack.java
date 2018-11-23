package game.state;

import game.world.Country;

public class Attack {
	private Country attackingCountry;
	private Country attackedCountry;
	private ArmyPlacement placement;
	private int armyTransferCount;

	public int getArmyTransferCount() {
		return armyTransferCount;
	}

	public void setArmyTransferCount(int armyTransferCount) {
		this.armyTransferCount = armyTransferCount;
	}
	
	public ArmyPlacement getPlacement() {
		return placement;
	}
	
	public void setPlacement(ArmyPlacement placement) {
		this.placement = placement;
	}
		
	public Attack(Country attackingCountry, Country attackedCountry) {
		this.attackingCountry = attackingCountry;
		this.attackedCountry = attackedCountry;
	}

	public Country getAttackingCountry() {
		return attackingCountry;
	}
	
	public void setAttackingCountry(Country attackingCountry) {
		this.attackingCountry = attackingCountry;
	}
	
	public Country getAttackedCountry() {
		return attackedCountry;
	}
	
	public void setAttackedCountry(Country attackedCountry) {
		this.attackedCountry = attackedCountry;
	}
}
