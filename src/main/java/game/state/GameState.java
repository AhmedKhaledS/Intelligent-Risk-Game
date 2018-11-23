package game.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.Player;
import game.world.Continent;
import game.world.Country;
import game.world.Graph;
import game.world.World;

public class GameState implements Cloneable, Comparable<GameState> {
	
	 private World worldState;
	 private Player playerTurn;
	 private GameState previousState;
	 private Integer cost = 0;
	 private Integer depth = 0;
	 private boolean prevTurnAttacked;
	 
	public boolean isPrevTurnAttacked() {
		return prevTurnAttacked;
	}

	public void setPrevTurnAttacked(boolean prevTurnAttacked) {
		this.prevTurnAttacked = prevTurnAttacked;
	}

	public void applyAttack(Attack attack) { // Just changes gameState (attacking, changes attacked country owner and number of troops with transfer)
		if (!isLegalAttack(attack)) {
			throw new RuntimeException("Cannot apply this (invalid) attack!");
		}
		// Placement is already done.
		this.worldState.getCountryById(attack.getAttackedCountry().getId()).setOwner(playerTurn);
		this.worldState.getCountryById(attack.getAttackedCountry().getId()).setArmiesSize(attack.getArmyTransferCount());		
		this.depth++;
		if (playerTurn == Player.PLAYER_1) {
			playerTurn = Player.PLAYER_2;
		} else {
			playerTurn = Player.PLAYER_1;
		}
	 }
	
	
	 
	 public boolean isLegalAttack(Attack attack) {
		 if (attack.getAttackingCountry().getOwner() == attack.getAttackedCountry().getOwner()) {
			 return false;
		 }
		 for (int adjacentCountryId : worldState.getGraph().getAdjacentCountries(attack.getAttackingCountry().getId())) {
			 if (attack.getAttackedCountry().getId() == adjacentCountryId
					  && attack.getAttackingCountry().getArmiesSize() > attack.getAttackedCountry().getArmiesSize() + 1) {
				 return true;
			 }
		 }
		 return false;
	 }

	public ArrayList<Attack> getLegalCountriesAttack() {
		ArrayList<Attack> adjacentOpponentCountries = new ArrayList<>();
		for (Country attackingCountry : getOwnedCountries()) {
			for (int attackedCountryId: worldState.getGraph().getAdjacentCountries(attackingCountry.getId())) {
				Country attackedCountry = worldState.getGraph().getCountryByIndex(attackedCountryId);
				if (attackedCountry.getOwner() != playerTurn
						&& attackingCountry.getArmiesSize() > attackedCountry.getArmiesSize() + 1) {
					adjacentOpponentCountries.add(new Attack(attackingCountry, attackedCountry));
				}
			}			
		}
		return adjacentOpponentCountries;
	}
	
	public void placeArmy(ArmyPlacement placement) {
		this.getWorldState().getCountryById(placement.getChosenCountry().getId())
							.setArmiesSize(this.getWorldState().getCountryById(placement.getChosenCountry().getId())
							.getArmiesSize() + placement.getTroopsCount());
	}
	
	/**
	 * Places the army in a new cloned game state.
	 * @param placement The specified placement based on the agent desire.
	 * @return the cloned game state after applying the placement.
	 */
	public GameState forecastArmyPlacement(ArmyPlacement placement) {
		GameState newState = clone();
		newState.getWorldState().getCountryById(placement.getChosenCountry().getId())
							.setArmiesSize(newState.getWorldState().getCountryById(placement.getChosenCountry().getId())
							.getArmiesSize() + placement.getTroopsCount());
		return newState;
	}
	
	public GameState forecastAttack(Attack attack) {
		if (!isLegalAttack(attack))
			return null; 
		GameState newState = this.clone();
		newState.applyAttack(attack);
		return newState;
	}
	
	public ArrayList<Country> getOwnedCountries() {
		ArrayList<Country> ownedCountries = new ArrayList<>();
		for (Continent continent: worldState.getContinents()) {
			for (Country country: continent.getCountires()) {
				if (country.getOwner() == playerTurn) {
					ownedCountries.add(country);
				}
			}
		}
		return ownedCountries;
	}
	
	public int getUtility() {
		return 0;
	}
	
	public void print() {
		
	}
	
	public boolean isTerminal() {
		boolean currentPlayerRulesWorld = true;
		boolean opponentPlayerRulesWorld = true;
		for (Continent continent: worldState.getContinents()) {
			for (Country country: continent.getCountires()) {
				if (country.getOwner() != playerTurn) {
					currentPlayerRulesWorld = false;
				} else {
					opponentPlayerRulesWorld = false;
				}
			}
		}
		if (currentPlayerRulesWorld || opponentPlayerRulesWorld) return true;
		// Skip case of this player
		return false;
	}
	
	public Player getWonPlayer() {
		if (isTerminal()) {
			return playerTurn;
		}
		return null;
	}
	
	public Player getLostPlayer() {
		if (isTerminal()) {
			return playerTurn == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;
		}
		return null;
	}
	
	public GameState clone() {
		GameState clonedState = new GameState();
		clonedState.setPlayerTurn(playerTurn);
		clonedState.setWorldState(worldState.clone());
		clonedState.setCost(cost);
		clonedState.setDepth(depth);
		clonedState.setPreviousState(previousState);
		return clonedState;
	}

	/**
	 * This function compares 2 game state and returns 0 if they are equal.
	 * @param state the compared game state.
	 * @return 0 if they are equal. O.W, it returns +ve or -ve value.
	 */
	@Override
	public int compareTo(GameState state) {
		
		if (worldState.equals(state.getWorldState()) && playerTurn == state.playerTurn) {
			return 0;
		}
		return -1;
	}	
	
	public boolean equals(Object o) {
	    if (o == this) {
	      return true;
	    } 
	    
	    if (!(o instanceof GameState)) {
	      return false;
	    }
	    
	    return compareTo((GameState)o) == 0;
	}
	
	public World getWorldState() {
		return worldState;
	}

	public void setWorldState(World worldState) {
		this.worldState = worldState;
	}

	public Player getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(Player playerTurn) {
		this.playerTurn = playerTurn;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public GameState getPreviousState() {
		return previousState;
	}

	public void setPreviousState(GameState previousState) {
		this.previousState = previousState;
	}
	
	
	public static void main(String[] args) throws CloneNotSupportedException {
		Country c1 = new Country();
		c1.setId(1);
		c1.setOwner(Player.PLAYER_1);
		Country c2 = new Country();
		c2.setId(2);
		c2.setOwner(Player.PLAYER_1);
		Country c3 = new Country();
		c3.setId(3);
		c3.setOwner(Player.PLAYER_2);
		Country c4 = new Country();
		c4.setId(4);
		c4.setOwner(Player.PLAYER_2);
		
		Continent cn1 = new Continent();
		cn1.setContinentBonus(10);
		cn1.setContinentID(1);
		ArrayList<Country> countries = new ArrayList<>();
		countries.add(c1);
		countries.add(c2);
		cn1.setCountires(countries);
		
		c1.setContinent(cn1);
		c2.setContinent(cn1);
		
		Continent cn2 = new Continent();
		cn2.setContinentBonus(20);
		cn2.setContinentID(2);
		ArrayList<Country> countries2 = new ArrayList<>();
		countries2.add(c3);
		countries2.add(c4);
		cn2.setCountires(countries2);
		
		c3.setContinent(cn2);
		c4.setContinent(cn2);
		
		World world = new World();
		ArrayList<Continent> continents1 = new ArrayList<>();
		continents1.add(cn1);
		continents1.add(cn2);
		world.setContinents(continents1);
		Graph g = new Graph();
		ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
		// Adj:
		// 0 1 2
		// 1 3 4
		adj.add(new ArrayList<>());
		adj.get(0).add(1);
		adj.get(0).add(2);
		adj.add(new ArrayList<>());
		adj.get(1).add(3);
		adj.get(1).add(4);
		g.setAdjacencyList(adj);
		Map<Integer, Country> mp = new HashMap<>();
		mp.put(1, c1);
		mp.put(2, c2);
		g.setCountriesIndex(mp);
		world.setGraph(g);
		GameState gs = new GameState();
		gs.setWorldState(world);
		gs.setPlayerTurn(Player.PLAYER_1);

		GameState clone = (GameState) gs.clone();
		System.out.println("gs1: " + gs + ",  gs2: " + clone);
//		clone.getWorldState().setContinents(null);
		System.out.println("1) size of continentsA: " + gs.getWorldState().getContinents().size() + ", size of continentsB: " + clone.getWorldState().getContinents().size());
		clone.getWorldState().getContinents().get(1).setContinentID(2);;
		System.out.println("2) size of continentsA: " + gs.getWorldState().getContinents().size() + ", size of continentsB: " + clone.getWorldState().getContinents().size());

		System.out.println("Equal: " + gs.equals((Object)clone));
	}
	
}
