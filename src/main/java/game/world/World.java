package game.world;

import java.util.ArrayList;
import java.util.Collections;

import game.util.ContinentsComparator;
import game.util.CountriesComparator;

public class World implements Comparable<World>, Cloneable {

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
	
	public Country getCountryById(int countryId) {
		Country foundCountry = null;
		for (Continent continent: continents) {
			foundCountry = continent.getCountryById(countryId);
			if (foundCountry != null) {
				return foundCountry;
			}
		}
		return foundCountry;
	}
	
	@Override
	public int compareTo(World arg0) {
		ArrayList<Continent> continentsA = this.getContinents();
		ArrayList<Continent> continentsB = arg0.getContinents();
		System.out.println("size of continentsA: " + continentsA.size() + ", size of continentsB: " + continentsB.size());;
		Collections.sort(continentsA, ContinentsComparator.getInstance());
		Collections.sort(continentsB, ContinentsComparator.getInstance());
		if (continentsA.equals(continentsB)) {
			return 0;
		}
		return -1;
	}

	public boolean equals(Object o) {
	    
	    if (!(o instanceof World)) {
	      return false;
	    }
	    
	    return compareTo((World)o) == 0;
	 }

	public World clone() {
		World clonedWorld = new World();
		ArrayList<Continent> clonedContinents = new ArrayList<>();
		for (Continent c : continents) {
			clonedContinents.add(c.clone());
		}
		clonedWorld.setContinents(clonedContinents);
		clonedWorld.setGraph(graph == null ? null : graph.clone());
		return clonedWorld;
	}

	
}
