package game.state;

import game.world.Country;

public class Attack {
	private Country attackingCountry;
	private Country attackedCountry;
	// here goes the rest of attributes denoting the army distribution and transfers after the attack.
	
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
