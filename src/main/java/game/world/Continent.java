package game.world;

import java.util.ArrayList;

import game.Player;

public class Continent {
	
	private int continentBonus;
	private ArrayList<Country> countries;

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
	
	public Player getContinentOwner() {
		if (!hasContinentOwner()) return null;
		return countries.get(0).getOwner();
	}
	
}
