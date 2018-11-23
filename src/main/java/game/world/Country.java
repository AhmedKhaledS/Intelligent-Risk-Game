package game.world;

import java.util.Comparator;

import game.Player;

public class Country implements Comparable<Country> {
	
	private Player owner;
	private int armySize;
	private int id;
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
	    
	    Country comparedCountry = (Country)o;
	    return comparedCountry.getId() == id; 
	  }

}
