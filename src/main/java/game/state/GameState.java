package game.state;

import java.util.ArrayList;
import java.util.List;

import game.Player;
import game.world.Continent;
import game.world.Country;
import game.world.World;

public class GameState implements Cloneable {
	
	private World worldState;
	private Player playerTurn;
	
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

	public ArrayList<Attack> getLegalCountriesAttack() {
		ArrayList<Attack> adjacentOpponentCountries = new ArrayList<>();
		for (Country attackingCountry : getOwnedCountries()) {
			for (Country attackedCountry: graph.getAdjacent(attackingCountry)) {
				if (attackedCountry.getOwner() != playerTurn) { // will be fixed on changing the Owner to Enum type.
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
	
	public void applyAttack(Attack attack) {
		if (playerTurn == Player.PLAYER_1)
			playerTurn = Player.PLAYER_2;
		else
			playerTurn = Player.PLAYER_1;
	}
	
	public boolean isLegalAttack(Attack attack) {
		
		return false;
	}
	
	public ArrayList<Country> getOwnedCountries() {
		ArrayList<Country> ownedCountries = new ArrayList<>();
		for (Continent continent: worldState.getContinents()) {
			for (Country country: continent.getCountires()) {
				if (country.getOwner() == playerTurn) { // will be fixed on changing the Owner to Enum type.
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
		return false;
	}
	
	public Player getWonPlayer() {
		return null;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return (GameState)super.clone();
	}
	
	
}
