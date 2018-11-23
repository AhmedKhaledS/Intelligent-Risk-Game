package agents.intelligent;

import java.util.ArrayList;
import java.util.PriorityQueue;

import agents.intelligent.heuristics.Heuristic;
import game.state.Attack;
import game.state.GameState;
import game.world.Country;

public class AStarAgent extends InformedSearchAgent {
	
	public AStarAgent(GameState initialState, Heuristic heuristic) {
		super(initialState, heuristic);
	}

	@Override
	public void search(GameState initialState) {
		PriorityQueue<GameState> searchQueue = new PriorityQueue<>();
		searchQueue.add(initialState);
		while (!searchQueue.isEmpty()) {
			GameState current = searchQueue.poll();
			/// TODO : get Turn Armies and Set in current GameState.
			ArrayList<Country> ownedCountries = current.getOwnedCountries();
			ArrayList<Attack> possibleAttacks = new ArrayList<>();
			for (Country country : ownedCountries) {
//				current set Armies in country
				ArrayList<Attack> legalAttacks = current.getLegalCountriesAttack();
				possibleAttacks.addAll(legalAttacks);
			}
				
			
			
		}
	}

	
}
