package agents;

import java.util.ArrayList;

import game.state.GameState;
import game.world.Country;

public class AggressiveAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		// TODO Auto-generated method stub

	}

	void place(GameState state) {
		
		// Get the owned countries from the "GameState" class
		ArrayList<Country> countries = state.getOwnedCountries();

		// Get the country with the maximum number of soldiers
		int maxCountryIndex = -1;
		int maxCountryValue = Integer.MIN_VALUE;

		for (Country country : countries) {
			int currValue = country.getArmiesSize();
			if (currValue > maxCountryValue) {
				maxCountryIndex = country.getIndex();
				maxCountryValue = currValue;
			}
		}
		
		System.out.println("Country #" + maxCountryIndex + "is chosen");

	}

	void attack() {

	}

	void transfer() {

	}

}
