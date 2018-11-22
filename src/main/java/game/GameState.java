package game;

import java.util.List;

import game.world.World;

public class GameState implements Cloneable {
	
	 World worldState;
	 CurrentPlayer playerTurn;
	
	public List<Move> getLegalMoves() {
		return null;
	}
	
	public GameState foreCastMove(Move move) {
		return null;
	}
	
	public void applyMove(Move move) {
		
	}
	
	public int getUtility() {
		return 0;
	}
	
	public void print() {
		
	}
	
	public boolean isTerminal() {
		return false;
	}
	
//	public PlayerEnum getWinningPlayer() {
//		return null;
//	}
	
	public Object clone() throws CloneNotSupportedException {
		return (GameState)super.clone();
	}
	
	
}
