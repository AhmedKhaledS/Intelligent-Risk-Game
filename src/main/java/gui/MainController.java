package gui;

import java.util.ArrayList;
import java.util.Scanner;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import agents.Agent;
import agents.AggressiveAgent;
import agents.HumanAgent;
import agents.PacifistAgent;
import agents.PassiveAgent;
import agents.intelligent.AStarAgent;
import agents.intelligent.GreedyAgent;
import agents.intelligent.RealTimeAStarAgent;
import agents.intelligent.heuristics.UnclaimedOptimiticHeuristic;
import agents.intelligent.heuristics.UnclaimedTerritoriesHeuristic;
import game.Player;
import game.controller.InputParser;
import game.state.GameState;
import game.world.Continent;
import game.world.Country;

public class MainController {
	
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
		while (!gs.isTerminal()) {
			currentAgent.takeTurn(gs);
			if (currentPlayer == Player.PLAYER_1) {
				currentPlayer = Player.PLAYER_2;
				currentAgent = secondAgent;
			} else {
				currentPlayer = Player.PLAYER_1;
				currentAgent = firstAgent;
			}
		}
		return gs.getWonPlayer();
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
			player = new AStarAgent(state, new PassiveAgent(), new UnclaimedOptimiticHeuristic());
			break;
		case 6:
			player = new GreedyAgent(state, new PassiveAgent(), new UnclaimedTerritoriesHeuristic());
			break;
		case 7:
			player = new RealTimeAStarAgent(state, new PassiveAgent(), new UnclaimedTerritoriesHeuristic(), 4);
		}
		return player;
	}
	
	public static void main(String args[]) {
		
		GameState gs = initializeGame("testOptimisticHeuristic.txt");
		System.out.println("Select agent for Player 1: 1) Aggressive  2) Passive  3) Pacifist  4) Human  5) A*  6) Greedy  7) Real Time A*");
		Scanner input = new Scanner(System.in);
		Agent player1 = null, player2 = null;
		Player wonPlayer = null;
		int agentId1 = input.nextInt();
		
		player1 = initiatePlayersTypes(gs, agentId1);
		
		
		if (agentId1 != 5 && agentId1 != 6 && agentId1 != 7) {
			System.out.println("Select agent for Player 2: 1) Aggressive  2) Passive  3) Pacifist  4) Human");
			int agentId2 = input.nextInt();
			player2 = initiatePlayersTypes(gs, agentId2);
		} else {
			player2 = new PassiveAgent();
		}
//		wonPlayer = run(gs, player1, player2);
//		System.out.println("Won player: " + wonPlayer);
		
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		SingleGraph graph = new SingleGraph("Use");
		String continentsColor[] = {"#3a54a5", "#ddda04", "#4f4c4c", "#60a568"};
		String playersColors[] = {"#161c4c", "#3d0101"};
		for (Continent continent : gs.getWorldState().getContinents()) {
			for (Country country : continent.getCountires()) {
				System.out.println("Node id: " + country.getId());
				graph.addNode(Integer.valueOf(country.getId()).toString());
				
				Node node = graph.getNode(Integer.valueOf(country.getId()).toString());
				node.addAttribute("ui.label", Integer.valueOf(country.getId()).toString());
				node.addAttribute("ui.style", "shape:circle;fill-color: "
						+ playersColors[gs.getPlayerTurn() == Player.PLAYER_1 ? 0 : 1]
						+ ";size: 90px; text-alignment: center; stroke-color: "
						+ continentsColor[gs.getWorldState().getGraph().getCountryByIndex(country.getId()).getContinent().getContinentID()]
						+ "; stroke-mode: plain; stroke-width: 5px; fill-mode: plain;");
			}
		}
		int nodeCount = 0;
		int edgeId = 1;
		for (ArrayList<Integer> adjacentNodes : gs.getWorldState().getGraph().getAdjacencyList()) {
			for (Integer countryId : adjacentNodes) {
				graph.addEdge(Integer.valueOf(edgeId++).toString(), Integer.valueOf(nodeCount).toString(), Integer.valueOf(countryId).toString(), true);
			}
			nodeCount++;
		}
		graph.display();
		
//	    graph.addNode("B");
//	    graph.addNode("C");
//	    graph.addNode("D");
//	    graph.addNode("E");
//	    graph.addNode("F");
//	    graph.addEdge("AB", "A", "B",true);
//	    graph.addEdge("BC", "B", "C",true);
//	    graph.addEdge("CA", "C", "A",true);
//	    graph.addEdge("CD", "C", "D",true);
//	    graph.addEdge("DF", "D", "F",true);
//	    graph.addEdge("EF", "E", "F",true);
//	    graph.addEdge("DE", "D", "E",true);
//	    Node e1=graph.getNode("A");
//	    e1.addAttribute("ui.style", "shape:circle;fill-color: blue;size: 90px; text-alignment: center;");
//	    e1.addAttribute("ui.label", "node A");
//	    graph.display();
	}
}