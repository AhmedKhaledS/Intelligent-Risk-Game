package game.world;

import java.util.ArrayList;

public class World {

	private ArrayList<Continent> continents;
	private Graph graph;

	public World() {
		continents = new ArrayList<>();
	}
	
	public ArrayList<Continent> getContients() {
		return continents;
	}

	public void setContients(ArrayList<Continent> contients) {
		this.continents = contients;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	
	
}
