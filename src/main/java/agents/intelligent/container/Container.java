package agents.intelligent.container;

import game.state.GameState;

public interface Container {

	public GameState getGameState();
	
	public void insertGameState();
	
	public boolean isEmpty();
}
