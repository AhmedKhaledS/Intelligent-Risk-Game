package game.world;

import java.util.ArrayList;

public class Graph {
	
	private ArrayList<ArrayList<Integer>> adjacencyList;
	
	public Graph() {
		
	}

	public ArrayList<ArrayList<Integer>> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(ArrayList<ArrayList<Integer>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

}