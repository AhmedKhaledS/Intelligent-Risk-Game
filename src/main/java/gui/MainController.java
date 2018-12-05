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
	
	private static String continentsColor[] = {"#3a54a5", "#ddda04", "#4f4c4c", "#60a568"};
	private static String playersColors[] = {"#b1e0e2", "#40ed48"};
	
	public static GameState initializeGame(String configFileName) {
		InputParser parser = new InputParser();
		return parser.parse(configFileName);
	}
	
	/**
	 * Returns the winning player.
	 * @throws InterruptedException 
	 */
	public static Player run(GameState gs, Agent firstAgent, Agent secondAgent, Graph graph) throws InterruptedException {
		Agent currentAgent = firstAgent;
		Player currentPlayer = Player.PLAYER_1;
		while (!gs.isTerminal()) {
			currentAgent.place(gs);
//			Thread.sleep(2000);
			updateGUIGameState(gs, graph);
			currentAgent.attack(gs);
//			Thread.sleep(2000);
			updateGUIGameState(gs, graph);
			currentAgent.transfer(gs);
//			Thread.sleep(2000);
			updateGUIGameState(gs, graph);
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
	
	
	private static Graph createGSGraph(GameState gs) {
		Graph graph = new SingleGraph("Risk");
		for (Continent continent : gs.getWorldState().getContinents()) {
			for (Country country : continent.getCountires()) {
				graph.addNode(Integer.valueOf(country.getId()).toString());
				Node node = graph.getNode(Integer.valueOf(country.getId()).toString());
				node.addAttribute("ui.label", "ID: " + Integer.valueOf(country.getId()).toString() + "   Troops: " + country.getArmiesSize());
				node.addAttribute("ui.style", "shape:circle;fill-color: "
						+ playersColors[country.getOwner() == Player.PLAYER_1 ? 0 : 1]
						+ ";size: 140px; text-alignment: center; text-size: 15; stroke-color: "
						+ continentsColor[gs.getWorldState().getGraph().getCountryByIndex(country.getId()).getContinent().getContinentID()]
						+ "; stroke-mode: plain; stroke-width: 6px; fill-mode: plain;");
			}
		}
		int nodeCount = 0;
		int edgeId = 1;
		for (ArrayList<Integer> adjacentNodes : gs.getWorldState().getGraph().getAdjacencyList()) {
			for (Integer countryId : adjacentNodes) {
				graph.addEdge(Integer.valueOf(edgeId++).toString(), 
						Integer.valueOf(nodeCount).toString(), Integer.valueOf(countryId).toString(), true);
			}
			nodeCount++;
		}
		return graph;
	}
	
	private static void updateGUIGameState(GameState gs, Graph graph) {
		for (Continent continent : gs.getWorldState().getContinents()) {
			for (Country country : continent.getCountires()) {
				Node node = graph.getNode(Integer.valueOf(country.getId()).toString());
				node.setAttribute("ui.label", "ID: " + Integer.valueOf(country.getId()).toString() + "   Troops: " + country.getArmiesSize());
				node.setAttribute("ui.style", "shape:circle;fill-color: "
						+ playersColors[country.getOwner() == Player.PLAYER_1 ? 0 : 1]
						+ ";size: 140px; text-alignment: center; text-size: 15; stroke-color: "
						+ continentsColor[gs.getWorldState().getGraph().getCountryByIndex(country.getId()).getContinent().getContinentID()]
						+ "; stroke-mode: plain; stroke-width: 6px; fill-mode: plain;");
			}
		}
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
		
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph = createGSGraph(gs);
		graph.display();
		try {
			wonPlayer = run(gs, player1, player2, graph);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Won player: " + wonPlayer);
	}
}