package agents.intelligent;

import java.util.ArrayList;

import agents.Agent;
import agents.intelligent.container.Container;
import game.state.Attack;
import game.state.GameState;

public abstract class SearchAgent implements Agent {

	protected ArrayList<Attack> moves;
	protected int index = 0;
	protected Agent opponentAgent;
	
	public SearchAgent(GameState initialState, Agent opponentAgent) {
		this.moves = new ArrayList<>();
		this.opponentAgent = opponentAgent;
		search(initialState);
	}
	
	public void search(GameState initialState) {
		
	}
	
	@Override
	public void takeTurn(GameState state) {
		// TODO : Place Army 
		// TODO : Attack
		// TODO : 
//		Attack attack = moves.get(index);
	}

	@Override
	public void place(GameState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attack(GameState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transfer(GameState state) {
		// TODO Auto-generated method stub

	}

}
