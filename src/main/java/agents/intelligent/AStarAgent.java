package agents.intelligent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import agents.Agent;
import agents.intelligent.heuristics.Heuristic;
import agents.util.BounsCalculator;
import game.state.Attack;
import game.state.GameState;
import game.world.Country;

public class AStarAgent extends InformedSearchAgent {
	
	public AStarAgent(GameState initialState, Agent opponentAgent, Heuristic heuristic) {
		super(initialState, opponentAgent, heuristic);
	}

	@Override
	public void search(GameState initialState) {
		PriorityQueue<GameState> searchQueue = new PriorityQueue<>();
		searchQueue.add(initialState);
		Set<GameState> visited = new HashSet<>();
		boolean firstTurn = true;
		BounsCalculator bonusCalculator = BounsCalculator.getInstance();
		while (!searchQueue.isEmpty()) {
			GameState current = searchQueue.poll();
			visited.add(current);
			if (!firstTurn) {
				opponentAgent.takeTurn(current);
			}
			int bonusArmies = bonusCalculator.getBonus(current, current.isPrevTurnAttacked());
			ArrayList<Country> ownedCountries = current.getOwnedCountries();
			ArrayList<Attack> possibleAttacks = new ArrayList<>();
			for (Country country : ownedCountries) {
				/// TODO : ArmyPlacement placement = new ArmyPlacement(country, armies [from above]);
				/// TODO : GameState virtual = forecastArmyPlacement(placement);
				ArrayList<Attack> legalAttacks = current.getLegalCountriesAttack(); /// TODO : current -> virtual
				/// TODO : Clone GameStates on the size of legalAttacks.
				possibleAttacks.addAll(legalAttacks);
			}
			for (Attack possibleAttack : possibleAttacks) {
				
			}
			
			
		}
	}

	
}
