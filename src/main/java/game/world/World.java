package game.world;

import java.util.ArrayList;
import java.util.Collections;

import game.util.ContinentsComparator;
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
		Collections.sort(continentsA, ContinentsComparator.getInstance());
		Collections.sort(continentsB, ContinentsComparator.getInstance());
		if (continentsA.equals(continentsB)) {
			return 0;
		}
		return -1;
	}

	public boolean equals(Object o) {
	    if (o == this) {
	      return true;
	    } 
	    
	    if (!(o instanceof World)) {
	      return false;
	    }
	    
	    return compareTo((World)o) == 0;
	 }


	
}
