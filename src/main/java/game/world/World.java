package game.world;

import java.util.ArrayList;

public class World {

	private ArrayList<Continent> continents;
	private Graph graph;

	public World() {
		continents = new ArrayList<>();
	}
	
	public ArrayList<Continent> getContinents() {
		return continents;
	}

	public void setContients(ArrayList<Continent> continents) {
		this.continents = continents;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	
	
}
