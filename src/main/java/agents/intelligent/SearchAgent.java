package agents.intelligent;

import java.util.ArrayList;

import agents.Agent;
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
		if (index >= moves.size()) {
			System.err.println("In Search Agent: No More Planned Moves to take!");
			return;
		}
		place(state);
		attack(state);
		Attack move = moves.get(index);
		int transfereCount = (move.getAttackingCountry().getArmiesSize() - move.getAttackedCountry().getArmiesSize()) / 2;

		System.out.println("Country #" + move.getAttackingCountry().getId() + " is attacking Country #" + move.getAttackedCountry().getId()
				+ " with transfere of " + transfereCount);
		index++;
		System.out.println("In Search Agent: Performed Move # " + index);
	}

	@Override
	public void place(GameState state) {
		if (index >= moves.size()) {
			System.err.println("In Search Agent: No More Planned Moves to take!");
			return;
		}
		Attack attack = moves.get(index);
		state.placeArmy(attack.getPlacement());
	}

	@Override
	public void attack(GameState state) {
		if (index >= moves.size()) {
			System.err.println("In Search Agent: No More Planned Moves to take!");
			return;
		}
		state.applyAttack(moves.get(index));
	}

	@Override
	public void transfer(GameState state) {
		// TODO Auto-generated method stub

	}

}
