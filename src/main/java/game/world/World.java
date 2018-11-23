package game.world;

import java.util.ArrayList;
import java.util.Collections;

import game.util.CountriesComparator;

public class World implements Comparable<World> {

	private ArrayList<Continent> continents;
	private Graph graph;

	public World() {
		continents = new ArrayList<>();
	}
	
	public ArrayList<Continent> getContinents() {
		return continents;
	}

	public void setContinents(ArrayList<Continent> continents) {
		this.continents = continents;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	public int compareTo(World arg0) {
		ArrayList<Continent> continentsA = this.getContinents();
		ArrayList<Continent> continentsB = arg0.getContinents();
		Collections.sort(continentsA, CountriesComparator.getInstance());
		Collections.sort(continentsB, CountriesComparator.getInstance());
		if (continentsA.equals(continentsB) && continentBonus == continent.getContinentBonus()) {
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
