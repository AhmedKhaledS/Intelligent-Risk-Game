package agents.intelligent;

import agents.Agent;
import agents.intelligent.heuristics.Heuristic;
import game.state.GameState;

public class InformedSearchAgent extends SearchAgent {

	protected Heuristic heuristic;
	
	public InformedSearchAgent(GameState initialState, Agent opponentAgent, Heuristic heuristic) {
		super(initialState, opponentAgent);
		this.heuristic = heuristic;
	}
}
