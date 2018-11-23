package agents.intelligent;

import agents.intelligent.heuristics.Heuristic;
import game.state.GameState;

public class InformedSearchAgent extends SearchAgent {

	protected Heuristic heuristic;
	
	public InformedSearchAgent(GameState initialState, Heuristic heuristic) {
		super(initialState);
		this.heuristic = heuristic;
	}
}
