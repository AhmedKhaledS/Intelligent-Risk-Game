package game.world;

import java.util.ArrayList;
import java.util.Collections;

import game.Player;
import game.util.CountriesComparator;

public class Continent implements Comparable<Continent> {
	
	private int continentBonus;
	private ArrayList<Country> countries;
	private int continentID;

	public Continent() {
		countries = new ArrayList<>();
	}
	
	public int getContinentBonus() {
		return continentBonus;
	}
	
	public void setContinentBonus(int continentBonus) {
		this.continentBonus = continentBonus;
	}
	
	public ArrayList<Country> getCountires() {
		return countries;
	}
	
	public void setCountires(ArrayList<Country> countires) {
		this.countries = countires;
	}
	
	public boolean hasContinentOwner() {
		if (countries.isEmpty()) return false;
		Player owner = countries.get(0).getOwner();
		for (Country country : countries) {
			if (country.getOwner() != owner)
				return false;
		}
		return true;
	}

	public int getContinentID() {
		return continentID;
	}

	public void setContinentID(int continentID) {
		this.continentID = continentID;
	}

	public Player getContinentOwner() {
		if (!hasContinentOwner()) return null;
		return countries.get(0).getOwner();
	}

	/**
	 * Comparing continents.
	 */
	@Override
	public int compareTo(Continent continent) {
		ArrayList<Country> countriesA = this.getCountires();
		ArrayList<Country> countriesB = continent.getCountires();
		Collections.sort(countriesA, CountriesComparator.getInstance());
		Collections.sort(countriesB, CountriesComparator.getInstance());
		if (countriesA.equals(countriesB) && continentBonus == continent.getContinentBonus()) {
			return 0;
		}
		return -1;
	}
	
	public boolean equals(Object o) {
	    if (o == this) {
	      return true;
	    } 
	    
	    if (!(o instanceof Continent)) {
	      return false;
	    }
	    
	    return compareTo((Continent)o) == 0;
	 }
	
}
