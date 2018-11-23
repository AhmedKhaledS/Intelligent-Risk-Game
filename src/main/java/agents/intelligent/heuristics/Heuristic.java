/**
 * 
 */
package agents.intelligent.heuristics;

import game.state.GameState;

/**
 * @author ahmednagga19
 *
 */
public interface Heuristic {
	
	public int getHeuristicValue(GameState state);
}
