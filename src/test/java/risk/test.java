package risk;

import game.state.GameState;

public class test {

	public static void main(String[] args) {
		GameState x = new GameState();
		GameState y;
		try {
			y = (GameState)x.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

}
