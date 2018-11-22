package game.state;

import java.util.ArrayList;

import game.Player;
import game.world.Continent;
import game.world.Country;
import game.world.Graph;
import game.world.World;

public class GameState implements Cloneable {
	
	 private World worldState;
	 private Player playerTurn;
	 private Graph graph;

	 public void applyAttack(Attack attack) {
		 if (playerTurn == Player.PLAYER_1)
			 playerTurn = Player.PLAYER_2;
		 else
			 playerTurn = Player.PLAYER_1;
	 }
	 
	 public boolean isLegalAttack(Attack attack) {
		 
		 for (int adjacentCountryId : graph.getAdjacentCountries(attack.getAttackingCountry().getIndex())) {
			 if (attack.getAttackedCountry().getIndex() == adjacentCountryId
					  && attack.getAttackingCountry().getArmiesSize() > attack.getAttackedCountry().getArmiesSize() + 1) {
				 return true;
			 }
		 }
		 return false;
	 }

	public ArrayList<Attack> getLegalCountriesAttack() {
		ArrayList<Attack> adjacentOpponentCountries = new ArrayList<>();
		for (Country attackingCountry : getOwnedCountries()) {
			for (int attackedCountryId: graph.getAdjacentCountries(attackingCountry.getIndex())) {
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
			return null; // or throw an exception, it will be revised later.
		GameState newState = null;
		try {
			newState = (GameState)this.clone();
			newState.setPlayerTurn(this.playerTurn == Player.PLAYER_1 ?
					Player.PLAYER_2 : Player.PLAYER_1);
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
		for (Continent continent: worldState.getContinents()) {
			for (Country country: continent.getCountires()) {
				if (country.getOwner() == playerTurn) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Player getWonPlayer() {
		if (isTerminal()) {
			return playerTurn == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;
		}
		return null;
	}
	
	public Player getLostPlayer() {
		if (isTerminal()) {
			return playerTurn;
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
	
	
	
}
