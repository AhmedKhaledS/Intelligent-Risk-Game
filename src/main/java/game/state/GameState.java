package game.state;

import java.util.ArrayList;

import game.Player;
import game.world.Continent;
import game.world.Country;
import game.world.Graph;
import game.world.World;

public class GameState implements Cloneable, Comparable<GameState> {
	
	 private World worldState;
	 private Player playerTurn;
	 private Graph graph;
	 private GameState previousState;
	 private Integer cost = 0;
	 private Integer depth = 0;

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

	public void applyAttack(Attack attack) {
		 if (playerTurn == Player.PLAYER_1)
			 playerTurn = Player.PLAYER_2;
		 else
			 playerTurn = Player.PLAYER_1;
		 // To be implemented later after adding the informing attributes of the attack (i.e Distribution & Transfers).
	 }
	 
	 public boolean isLegalAttack(Attack attack) {
		 
		 for (int adjacentCountryId : graph.getAdjacentCountries(attack.getAttackingCountry().getId())) {
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
			for (int attackedCountryId: graph.getAdjacentCountries(attackingCountry.getId())) {
				Country attackedCountry = graph.getCountryByIndex(attackedCountryId);
				if (attackedCountry.getOwner() != playerTurn) {
					adjacentOpponentCountries.add(new Attack(attackingCountry, attackedCountry));
				}
			}			
		}
		return adjacentOpponentCountries;
	}
	
	public GameState forecastAttack(Attack attack) {
		if (!isLegalAttack(attack))
			return null; 
		GameState newState = null;
		try {
			newState = (GameState)this.clone();
			newState.applyAttack(attack);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
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
	
	/**
	 * Searches for a country owner that his turn is now, if found, then it's not a terminal state.
	 * @return true indicating that it's a terminal state, otherwise it returns false.
	 */
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
	
	public Object clone() throws CloneNotSupportedException {
		return (GameState)super.clone();
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

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
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
	
}
