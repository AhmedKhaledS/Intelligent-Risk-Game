package game.world;

import java.util.ArrayList;

public class Graph {
	
	private ArrayList<ArrayList<Integer>> adjacencyList;
	
	public Graph() {
		adjacencyList = new ArrayList<>();
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
}