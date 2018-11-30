package agents.intelligent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import agents.Agent;
import agents.PassiveAgent;
import agents.intelligent.heuristics.Heuristic;
import agents.intelligent.heuristics.UnclaimedTerritoriesHeuristic;
import agents.util.BounsCalculator;
import game.controller.InputParser;
import game.state.ArmyPlacement;
import game.state.Attack;
import game.state.GameState;
import game.world.Country;

public class GreedyAgent extends InformedSearchAgent {

	public GreedyAgent(GameState initialState, Agent opponentAgent, Heuristic heuristic) {
		super(initialState, opponentAgent, heuristic);
		search(initialState);
	}
	
	@Override
	public void search(GameState initialState) {
		PriorityQueue<GameState> searchQueue = new PriorityQueue<>();
		searchQueue.add(initialState);
		Set<GameState> visited = new HashSet<>();
		boolean firstTurn = true;
		BounsCalculator bonusCalculator = BounsCalculator.getInstance();
		Map<GameState, Attack> attacks = new HashMap<>();
		while (!searchQueue.isEmpty()) {
			GameState current = searchQueue.poll();
			if (current.isTerminal()) {							// Terminal State : backtracking to get the list of attacks done in the path to terminal state.
				while (current.getPreviousState() != null) {
					moves.add(attacks.get(current));
					current = current.getPreviousState();
				}
				Collections.reverse(moves);
				System.out.println(moves.size());
				return;
			}
			visited.add(current);
			firstTurn = false;
			int bonusArmies = bonusCalculator.getBonus(current, current.isPrevTurnAttacked());
			ArrayList<Country> ownedCountries = current.getOwnedCountries();
			for (Country country : ownedCountries) {			// Loop over all possible placement of armies (Over owned countries).
				ArmyPlacement placement = new ArmyPlacement(country, bonusArmies);
				GameState virtual = current.forecastArmyPlacement(placement);
				GameState virtualTest = virtual.clone();
				ArrayList<Attack> possibleAttacks = virtual.getLegalCountriesAttack();
				Attack nonAttack = new Attack(null, null);		// Add the state reached when no attack is made.
				nonAttack.setPlacement(placement.clone());
				virtual.setCost(heuristic.getHeuristicValue(virtual));
				virtual.setPrevTurnAttacked(false);
				virtual.applyAttack(nonAttack);
				virtual.setPreviousState(current);
				opponentAgent.takeTurn(virtual);
				addGameState(searchQueue, visited, virtual);
				attacks.put(virtual, nonAttack);
				if (!possibleAttacks.isEmpty()) {				// Handle possible attacks and check for each one of them the possible transfer of armies.
					for (Attack possibleAttack : possibleAttacks) {
						Country attacking = possibleAttack.getAttackingCountry();
						int armies = attacking.getArmiesSize() - possibleAttack.getAttackedCountry().getArmiesSize();
						for (int onAttacking = 1; onAttacking < armies; onAttacking++) {		// Loop over all possible transfer of armies from attacking to attacked.
							int onAttacked = armies - onAttacking;
							Attack newAttack = possibleAttack.clone();
							newAttack.setArmyTransferCount(onAttacked);
							newAttack.setPlacement(placement.clone());
							GameState newVirtual = virtualTest.clone();
							newVirtual.setPrevTurnAttacked(true);
							newVirtual.setCost(heuristic.getHeuristicValue(newVirtual));
							newVirtual.applyAttack(newAttack);
							newVirtual.setPreviousState(current);
							opponentAgent.takeTurn(newVirtual);
							addGameState(searchQueue, visited, newVirtual);
							attacks.put(newVirtual, newAttack);
						}
					}
				}
			}
			
		}
	}

	public void addGameState(PriorityQueue<GameState> searchQueue, Set<GameState> visited, GameState state) {
		if (!visited.contains(state)) {
			searchQueue.add(state);
		}
		return;
	}

	public static void main(String[] args) {
		InputParser parser = new InputParser();
		GameState state = parser.parse("config.txt");
		GreedyAgent agent = new GreedyAgent(state, new PassiveAgent(), new UnclaimedTerritoriesHeuristic());

	}

}
