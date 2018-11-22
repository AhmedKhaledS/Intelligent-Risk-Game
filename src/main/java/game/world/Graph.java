package game.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
	
	private ArrayList<ArrayList<Integer>> adjacencyList;
	private Map<Integer, Country> countriesIndex;

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
}