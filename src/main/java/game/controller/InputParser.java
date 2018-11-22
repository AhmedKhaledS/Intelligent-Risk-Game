package game.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import game.state.GameState;

public class InputParser {

	
	public InputParser() {
		
	}
	
	public GameState parse(String configFileName) {
		ArrayList<String> fileLines = readFileLines(configFileName);
		int verticesCount = Integer.parseInt(fileLines.get(0));
		int edgesCount = Integer.parseInt(fileLines.get(1));
		
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
}
