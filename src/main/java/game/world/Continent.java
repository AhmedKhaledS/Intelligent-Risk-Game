package game.world;

import java.util.ArrayList;

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
	
}
