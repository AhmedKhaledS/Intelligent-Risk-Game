package agents;

import java.util.ArrayList;

import game.state.GameState;
import game.world.Country;

public class PassiveAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		// TODO Auto-generated method stub

	}

	void place(GameState state) {

		// Get the owned countries from the "GameState" class
		ArrayList<Country> countries = state.getOwnedCountries();

		// Get the country with the minimum number of soldiers
		int minCountryIndex = -1;
		int minCountryValue = Integer.MAX_VALUE;

		for (Country country : countries) {
			int currValue = country.getArmiesSize();
			if (currValue < minCountryValue) {
				minCountryIndex = country.getIndex();
				minCountryValue = currValue;
			}
		}
		
		System.out.println("Country #" + minCountryIndex + "is chosen");
		
	}

	void attack() {
		// I will not attack ^_^
	}

	void transfer() {
		// I will not transfer too ^_^
	}
}
