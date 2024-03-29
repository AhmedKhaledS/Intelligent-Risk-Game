package game.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import agents.Agent;
import agents.AggressiveAgent;
import agents.PassiveAgent;
import game.Player;
import game.state.GameState;
import game.world.Continent;
import game.world.Country;
import game.world.Graph;
import game.world.World;

public class InputParser {

	public InputParser() {

	}

	public GameState parse(String configFileName) {
		int index = 0;
		ArrayList<String> fileLines = readFileLines(configFileName);
		int verticesCount = Integer.parseInt(fileLines.get(0));
		ArrayList<Country> countries = createCountriesList(fileLines, 1, verticesCount);
		int edgesCount = Integer.parseInt(fileLines.get(verticesCount + 1));
		ArrayList<ArrayList<Integer>> adjacencyList = buildAdjacencyList(fileLines, verticesCount + 2, edgesCount,
				verticesCount);
		index += verticesCount + 2 + edgesCount;
		int continentsCount = Integer.parseInt(fileLines.get(index));
		ArrayList<Continent> continents = buildContinentsList(fileLines, ++index, continentsCount, countries);
		index += continentsCount;
		World world = new World();
		world.setContinents(continents);
		Graph graph = new Graph();
		graph.setAdjacencyList(adjacencyList);
		buildCountriesMap(countries, graph);
		world.setGraph(graph);
		GameState state = new GameState();
		state.setWorldState(world);
		state.setPlayerTurn(Player.PLAYER_1);
		return state;
	}

	private ArrayList<String> readFileLines(String configFileName) {
		BufferedReader bufferedReader;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			bufferedReader = new BufferedReader(new FileReader(configFileName));
			String line = "";
			while ((line) != null) {
				String line_read = bufferedReader.readLine();
				if (line_read != null) {
					lines.add(line_read);
				}
				line = line_read;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.err.println("Input Config File Not Found!");
		} catch (IOException e) {
			System.err.println("Cannot Close File");
		}
		return lines;
	}

	private void initializeAdjacencyList(ArrayList<ArrayList<Integer>> list, int n) {
		for (int i = 0; i < n + 1; i++) {
			list.add(new ArrayList<>());
		}
		return;
	}

	private ArrayList<Country> createCountriesList(ArrayList<String> lines, int startIndex, int vertices) {
		ArrayList<Country> countries = new ArrayList<>();
		for (int i = startIndex, j = 1; i < startIndex + vertices; i++, j++) {
			String[] countryDescriptor = lines.get(i).split(" ");
			Integer playerID = Integer.parseInt(countryDescriptor[0]);
			Integer armiesSize = Integer.parseInt(countryDescriptor[1]);
			Country country = new Country();
			country.setArmiesSize(armiesSize);
			country.setId(j);
			country.setOwner((playerID == 1 ? Player.PLAYER_1 : Player.PLAYER_2));
			countries.add(country);
		}
		return countries;
	}

	private ArrayList<ArrayList<Integer>> buildAdjacencyList(ArrayList<String> lines, int startIndex, int edges,
			int verticesCount) {
		ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
		initializeAdjacencyList(adjacencyList, verticesCount);
		for (int i = startIndex; i < startIndex + edges; i++) {
			String[] endpointStrings = (lines.get(i)).split(" ");
			Integer from = Integer.parseInt(endpointStrings[0]);
			Integer to = Integer.parseInt(endpointStrings[1]);
			adjacencyList.get(from).add(to);
			adjacencyList.get(to).add(from);
		}
		return adjacencyList;
	}

	private ArrayList<Continent> buildContinentsList(ArrayList<String> lines, int startIndex, int continentsCount,
			ArrayList<Country> countries) {
		int index = 1;
		ArrayList<Continent> continents = new ArrayList<>();
		for (int i = startIndex; i < startIndex + continentsCount; i++) {
			String[] continentDescriptor = lines.get(i).split(" ");
			Integer continentBonus = Integer.parseInt(continentDescriptor[0]);
			Integer continentSize = Integer.parseInt(continentDescriptor[1]);
			ArrayList<Country> continentCountries = new ArrayList<>();
			for (int j = 0; j < continentSize; j++) {
				continentCountries.add(countries.get(Integer.parseInt(continentDescriptor[j + 2]) - 1));
			}
			Continent continent = new Continent();
			continent.setContinentBonus(continentBonus);
			continent.setCountires(continentCountries);
			continent.setContinentID(index++);
			for (Country country : continentCountries) {
				country.setContinent(continent);
			}
			continents.add(continent);
		}
		return continents;
	}

	private void buildCountriesMap(ArrayList<Country> countries, Graph graph) {
		for (Country country : countries) {
			graph.setCountryIndex(country, country.getId());
		}
		return;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		// InputParser parser = new InputParser();
		// GameState state = parser.parse("config.txt");
		// System.out.println("Finished Parsing");
		// System.out.println("Printing some values to validate parsing: \n Turn: " +
		// state.getPlayerTurn());
		// GameState newState = (GameState)state.clone();
		// ArrayList<Country> ownedCountries = newState.getOwnedCountries();
		// System.out.println("Ownded Countries: " + ownedCountries.get(0).getId() + ",
		// " + ownedCountries.get(1).getId());
		// ArrayList<Attack> attacks = newState.getLegalCountriesAttack();
		// for (Attack atk : attacks) {
		// System.out.println("attacking country: " + atk.getAttackingCountry().getId()
		// + ", attacked countries: " + atk.getAttackedCountry().getId());
		// }
		// System.out.println("Terminal State: " + newState.isTerminal());
		// System.out.println("Won Player: " + newState.getWonPlayer());
		// System.out.println("Lost Player: " + newState.getLostPlayer());

		// 1. Create the initial game state...
		InputParser parser = new InputParser();
		GameState state = parser.parse("config.txt");

		// 2. Check the first player ^_^
		// 2.1. His name ^_^
		System.out.println("POINT 2");
		System.out.println(state.getPlayerTurn());
		// 2.2. His countries ^_^
		ArrayList<Country> ownedCountries = state.getOwnedCountries();
		for (int i = 0; i < ownedCountries.size(); i++) {
			System.out.println("Ownded Countries: " + ownedCountries.get(i).getId() + " "
					+ ownedCountries.get(i).getArmiesSize());
		}

		// 3. Create our simple agent
		Agent agent = new AggressiveAgent();
		
		// 3.1. Simple Agent: PLACE
		agent.place(state);
		// 3.1.1 His name ^_^
		System.out.println("POINT 3.1");
		System.out.println(state.getPlayerTurn());
		// 3.1.1 His countries ^_^
		ownedCountries = state.getOwnedCountries();
		for (int i = 0; i < ownedCountries.size(); i++) {
			System.out.println("Ownded Countries: " + ownedCountries.get(i).getId() + " "
					+ ownedCountries.get(i).getArmiesSize());
		}

		// 3.2. Simple Agent: ATTACK
		agent.attack(state);
		// 3.2.1 His name ^_^
		System.out.println("POINT 3.2");
		System.out.println(state.getPlayerTurn());
		// 3.1.1 His countries ^_^
		ownedCountries = state.getOwnedCountries();
		for (int i = 0; i < ownedCountries.size(); i++) {
			System.out.println("Ownded Countries: " + ownedCountries.get(i).getId() + " "
					+ ownedCountries.get(i).getArmiesSize());
		}

		// System.out.println;
		// System.out.println("Finished Parsing");
	}
}
