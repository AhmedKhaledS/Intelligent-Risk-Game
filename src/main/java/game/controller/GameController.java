package game.controller;

import java.util.Scanner;
import agents.Agent;
import agents.AggressiveAgent;
import agents.HumanAgent;
import agents.PacifistAgent;
import agents.PassiveAgent;
import agents.intelligent.AStarAgent;
import agents.intelligent.heuristics.UnclaimedTerritoriesHeuristic;
import game.Player;
import game.state.GameState;

public class GameController {

	public static GameState initializeGame(String configFileName) {
		InputParser parser = new InputParser();
		return parser.parse(configFileName);
	}
	
	/**
	 * Returns the winning player.
	 */
	public static Player run(GameState gs, Agent firstAgent, Agent secondAgent) {
		Agent currentAgent = firstAgent;
		Player currentPlayer = Player.PLAYER_1;
		while (true) {
			currentAgent.takeTurn(gs);
			if (currentPlayer == Player.PLAYER_1) {
				currentPlayer = Player.PLAYER_2;
				currentAgent = secondAgent;
			} else {
				currentPlayer = Player.PLAYER_1;
				currentAgent = firstAgent;
			}
		}
	}
	
	private static Agent initiatePlayersTypes(GameState state, int agentId) {
		Agent player = null;
		switch (agentId) {
		case 1: 
			player = new AggressiveAgent();
			break;
		case 2:
			player = new PassiveAgent();
			break;
		case 3:
			player = new PacifistAgent();
			break;
		case 4:
			player = new HumanAgent();
			break;
		case 5:
			player = new AStarAgent(state, new PassiveAgent(), new UnclaimedTerritoriesHeuristic());
//			player2 = new PassiveAgent();
			break;
		case 6:
//			player = new GreedyAgent(state, new PassiveAgent(), new UnclaimedTerritoriesHeuristic());
			break;
		}
		return player;
	}
	
	public static void main(String[] args) {
		GameState gs = initializeGame("config.txt");
		System.out.println("Select agent for Player 1: 1) Aggressive  2) Passive  3) Pacifist  4) Human  5) A*  6) Greedy");
		Scanner input = new Scanner(System.in);
		Agent player1 = null, player2 = null;
		Player wonPlayer = null;
		int agentId1 = input.nextInt();
		
		player1 = initiatePlayersTypes(gs, agentId1);
		
		
		if (agentId1 != 5 && agentId1 != 6) {
			System.out.println("Select agent for Player 2: 1) Aggressive  2) Passive  3) Pacifist  4) Human");
			int agentId2 = input.nextInt();
			player2 = initiatePlayersTypes(gs, agentId2);
		} else {
			player2 = new PassiveAgent();
		}
		wonPlayer = run(gs, player1, player2);
		System.out.println("Won player: " + wonPlayer);
	}

}
