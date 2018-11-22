package game.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import game.state.GameState;
import game.world.Country;

public class InputParser {

	
	public InputParser() {
		
	}
	
	public GameState parse(String configFileName) {
		ArrayList<String> fileLines = readFileLines(configFileName);
		int verticesCount = Integer.parseInt(fileLines.get(0));
		ArrayList<Country> countries = new ArrayList<>();
		
		int edgesCount = Integer.parseInt(fileLines.get(verticesCount + 1));
		ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
		initializeAdjacencyList(adjacencyList, verticesCount);
		int i = verticesCount + 2;
		for (; i < edgesCount + 2; i++) {
			String[] endpointStrings = (fileLines.get(i)).split(" ");
			Integer from = Integer.parseInt(endpointStrings[0]);
			Integer to  = Integer.parseInt(endpointStrings[1]);
			adjacencyList.get(from).add(to);
			adjacencyList.get(to).add(from);
		}
		int noOfContinents = Integer.parseInt(fileLines.get(i++));
		for (int j = 0; j < noOfContinents; i++, j++) {
			
		}
		return null;
	}
	
	private ArrayList<String> readFileLines(String configFileName) {
		BufferedReader bufferedReader;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			bufferedReader = new BufferedReader(new FileReader(configFileName));
			String line = null;
			while((line) != null) {
				String line_read =  bufferedReader.readLine();
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
		for (int i = 0; i < n; i++) {
			list.add(new ArrayList<>());
		}
		return;
	}
	
	private ArrayList<Country> createCountriesList(ArrayList<String> lines, int startIndex, int vertices) {
		for (int i = startIndex, j = 0; i < startIndex + vertices; i++, j++) {
			String[] countryDescriptor = lines.get(i).split(" ");
			Integer playerID = Integer.parseInt(countryDescriptor[0]);
			Integer armiesSize = Integer.parseInt(countryDescriptor[1]);
			Country country = new Country();
			country.setArmiesSize(armiesSize);
		}
		return null;
	}
}
