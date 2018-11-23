package agents.intelligent;

import agents.intelligent.heuristics.Heuristic;
import game.state.GameState;

public class AStarAgent extends InformedSearchAgent {
	
	public AStarAgent(GameState initialState, Heuristic heuristic) {
		super(initialState, heuristic);
	}

	@Override
	public void search(GameState initialState) {
//		PriorityQueue<>
		super.search(initialState);
	}

	
}
