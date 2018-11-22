package agents;

import game.state.GameState;

public interface Agent {

	void takeTurn(GameState state);

	void place(GameState state);

	void attack(GameState state);

	void transfer(GameState state);

}
