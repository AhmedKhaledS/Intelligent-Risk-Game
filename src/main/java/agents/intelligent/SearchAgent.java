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
	}
	
	public void search(GameState initialState) {
		
	}
	
	@Override
	public void takeTurn(GameState state) { 
		place(state);
		attack(state);
		index++;
	}

	@Override
	public void place(GameState state) {
		Attack attack = moves.get(index);
		state.placeArmy(attack.getPlacement());
	}

	@Override
	public void attack(GameState state) {
		state.applyAttack(moves.get(index));
	}

	@Override
	public void transfer(GameState state) {
		// TODO Auto-generated method stub

	}

}
