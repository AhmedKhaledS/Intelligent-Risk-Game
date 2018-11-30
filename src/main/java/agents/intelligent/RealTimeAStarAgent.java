package agents.intelligent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import agents.Agent;
import agents.intelligent.heuristics.Heuristic;
import agents.util.BounsCalculator;
import game.state.ArmyPlacement;
import game.state.Attack;
import game.state.GameState;
import game.world.Country;

public class RealTimeAStarAgent extends InformedSearchAgent {

	private int depth;
	
	public RealTimeAStarAgent(GameState initialState, Agent opponentAgent, Heuristic heuristic, int depth) {
		super(initialState, opponentAgent, heuristic);
		this.depth = depth;
	}

	@Override
	public void search(GameState initialState) {
		PriorityQueue<GameState> searchQueue = new PriorityQueue<>();
		searchQueue.add(initialState);
		Set<GameState> visited = new HashSet<>();
		boolean firstTurn = true;
		BounsCalculator bonusCalculator = BounsCalculator.getInstance();
		Map<GameState, Attack> attacks = new HashMap<>();
		int alpha = Integer.MAX_VALUE;
		while (!searchQueue.isEmpty()) {
			GameState current = searchQueue.poll();
			if (!firstTurn) {  									//	Opponent Agent does not take turn in first iteration. (Player 1 starts)		
				opponentAgent.takeTurn(current);
			}
			if (current.isTerminal() || (allHaveSameDepth(searchQueue) && current.getDepth() == depth)) { // Terminal State : backtracking to get the list of attacks done in the path to terminal state.
				while (current.getPreviousState() != null) {
					moves.add(attacks.get(current));
					current = current.getPreviousState();
				}
				Collections.reverse(moves);
				System.out.println("Moves Size: " + moves.size());
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
				virtual.setCost(heuristic.getHeuristicValue(virtual) + virtual.getDepth());
				virtual.setPrevTurnAttacked(false);
				virtual.applyAttack(nonAttack);
				virtual.setPreviousState(current);
//				if (virtual.getCost() < alpha) {					
					addGameState(searchQueue, visited, virtual);
					attacks.put(virtual, nonAttack);
					alpha = virtual.getCost();
//				}
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
							newVirtual.setCost(heuristic.getHeuristicValue(newVirtual) + newVirtual.getDepth());
							newVirtual.applyAttack(newAttack);
							newVirtual.setPreviousState(current);
//							if (newVirtual.getCost() < alpha) {								
								addGameState(searchQueue, visited, newVirtual);
								attacks.put(newVirtual, newAttack);
								alpha = virtual.getCost();
//							}
						}
					}
				}
			}
			
		}
	}

	public void addGameState(PriorityQueue<GameState> searchQueue, Set<GameState> visited, GameState state) {
		if (!visited.contains(state) && state.getDepth() <= this.depth) {
			searchQueue.add(state);
		}
//		System.out.println(" Search Queue size : " + searchQueue.size());
		return;
	}
	
	
	private boolean allHaveSameDepth(PriorityQueue<GameState> searchQueue) {
		Iterator<GameState> it = searchQueue.iterator();
		while (it.hasNext()) {
			GameState current = it.next();
			if (current.getDepth() != depth) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void takeTurn(GameState state) {
		moves.clear();
		search(state);
		place(state);
		attack(state);
		
	}

	@Override
	public void place(GameState state) {
		Attack attack = moves.get(0);
		state.placeArmy(attack.getPlacement());
	}

	@Override
	public void attack(GameState state) {
		state.applyAttack(moves.get(0));
	}

	
	
}
