package game.world;

import game.Player;

/**
 * @author ahmednagga19
 * Class Country represents a country on the risk map including the owner player, army size, and country id.
 */
public class Country implements Comparable<Country>, Cloneable {
	
	/**
	 * Owner player of this country
	 */
	private Player owner;
	/**
	 * army size located in this country by this player
	 */
	private int armySize;
	/**
	 * Country id
	 */
	private int id;
	/**
	 * The continent to which this country belongs
	 */
	private Continent continent;
	
	public Continent getContinent() {
		return continent;
	}
	
	public void setContinent(Continent continent) {
		this.continent = continent;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int index) {
		this.id = index;
	}
	
	public int getArmiesSize() {
		return armySize;
	}
	
	public void setArmiesSize(int armiesSize) {
		this.armySize = armiesSize;
	}
	
	@Override
	public int compareTo(Country country) {
		if (owner == country.getOwner() && id == country.getId()
				&& armySize == country.getArmiesSize()) {
			return 0; // the same
		}
		return -1; // different
	}
	
	public boolean equals(Object o) {
	    if (o == this) {
	      return true;
	    } 
	    
	    if (!(o instanceof Country)) {
	      return false;
	    }
	    
	    return compareTo((Country) o) == 0;
	 }
	
	public Country clone() {
		Country clonedCountry = new Country();
		clonedCountry.setId(id);
		clonedCountry.setOwner(owner);
		clonedCountry.setArmiesSize(armySize);
		clonedCountry.setContinent(continent);
		return clonedCountry;
	}

}
