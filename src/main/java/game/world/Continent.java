package game.world;

import java.util.ArrayList;

public class Continent {
	
	private int continentBonus;
	private ArrayList<Country> countires;
	
	public int getContinentBonus() {
		return continentBonus;
	}
	
	public void setContinentBonus(int continentBonus) {
		this.continentBonus = continentBonus;
	}
	
	public ArrayList<Country> getCountires() {
		return countires;
	}
	
	public void setCountires(ArrayList<Country> countires) {
		this.countires = countires;
	}
	
}
