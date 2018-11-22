package game.state;

import java.util.ArrayList;
import java.util.List;

import game.CurrentPlayer;
import game.world.Country;
import game.world.World;

public class GameState implements Cloneable {
	
	 private World worldState;
	 private CurrentPlayer playerTurn;
	
	public World getWorldState() {
		return worldState;
	}

	public void setWorldState(World worldState) {
		this.worldState = worldState;
	}

	public CurrentPlayer getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(CurrentPlayer playerTurn) {
		this.playerTurn = playerTurn;
	}

	public List<Move> getLegalMoves() {
		return null;
	}
	
	public GameState forecastMove(Move move) {
		if (!isLegalMove(move))
			return null; // or throw an exception, it will be revised later.
		GameState newState = null;
		try {
			newState = (GameState)this.clone();
			newState.setPlayerTurn(this.playerTurn == CurrentPlayer.PLAYER_1 ?
					CurrentPlayer.PLAYER_2 : CurrentPlayer.PLAYER_1);
			newState.applyMove(move);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return newState;
	}
	
	public void applyMove(Move move) {
		if (playerTurn == CurrentPlayer.PLAYER_1)
			playerTurn = CurrentPlayer.PLAYER_2;
		else
			playerTurn = CurrentPlayer.PLAYER_1;
	}
	
	public boolean isLegalMove(Move move) {
		return false;
	}
	
	public ArrayList<Country> getOwnedCountries() {
		for (Continent continent: this.worldState.getContinents())
	}
	
	public int getUtility() {
		return 0;
	}
	
	public void print() {
		
	}
	
	public boolean isTerminal() {
		return false;
	}
	
	public CurrentPlayer getWonPlayer() {
		return null;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return (GameState)super.clone();
	}
	
	
}
