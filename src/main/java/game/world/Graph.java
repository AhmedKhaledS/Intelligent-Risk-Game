package game.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ahmednagga19
 * Class Graph represents the adjacency list of the world countries.
 */
public class Graph implements Cloneable {
	
	/**
	 * Adjacency list for the graph.
	 */
	private ArrayList<ArrayList<Integer>> adjacencyList;
	/**
	 * Id for each country.
	 */
	private Map<Integer, Country> countriesIndex;

	public Map<Integer, Country> getCountriesIndex() {
		return countriesIndex;
	}

	public void setCountriesIndex(Map<Integer, Country> countriesIndex) {
		this.countriesIndex = countriesIndex;
	}

	public Graph() {
		adjacencyList = new ArrayList<>();
		countriesIndex = new HashMap<>();
	}

	public ArrayList<ArrayList<Integer>> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(ArrayList<ArrayList<Integer>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

	public ArrayList<Integer> getAdjacentCountries(int index) {
		return adjacencyList.get(index);
	}
	
	public Country getCountryByIndex(int index) {
		return countriesIndex.get(index);
	}
	
	public void setCountryIndex(Country country, int index) {
		this.countriesIndex.put(index, country);
	}
	
	public Graph clone() {
		Graph clonedGraph = new Graph();
		ArrayList<ArrayList<Integer>> clonedAdjList = new ArrayList<>();
		for (ArrayList<Integer> adjacentList : adjacencyList) {
			clonedAdjList.add(new ArrayList<>());
			for (Integer id : adjacentList) {
				clonedAdjList.get(clonedAdjList.size()-1).add(id);
			}
		}
		clonedGraph.setAdjacencyList(clonedAdjList);
		clonedGraph.setCountriesIndex(copy(countriesIndex));
		return clonedGraph;
	}

	private Map<Integer, Country> copy(Map<Integer, Country> original)
	{
	    HashMap<Integer, Country> copy = new HashMap<>();
	    for (Map.Entry<Integer, Country> entry : original.entrySet()) {
	        copy.put(entry.getKey(), entry.getValue().clone());
	    }
	    return copy;
	}
	
}