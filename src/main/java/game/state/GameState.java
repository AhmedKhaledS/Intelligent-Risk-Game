package game.state;

import java.util.List;

import game.Player;
import game.world.Graph;
import game.world.World;

public class GameState implements Cloneable {
	
	 private World worldState;
	 private Player playerTurn;
	 private Graph graph;
	
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
