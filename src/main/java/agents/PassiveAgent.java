package agents;

import java.util.ArrayList;

import game.state.GameState;
import game.world.Country;

public class PassiveAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void place(GameState state) {

		// Get the owned countries from the "GameState" class
		ArrayList<Country> countries = state.getOwnedCountries();

		// Get the country with the minimum number of soldiers
		Country minCountry = null;
		int minCountryValue = Integer.MAX_VALUE;
		int minCountryIndex = -1;

		for (Country country : countries) {
			int currValue = country.getArmiesSize();
			int currIndex = country.getId();
			if (currValue < minCountryValue) {
				minCountry = country;
				minCountryValue = currValue;
				minCountryIndex = currIndex;
			} else if (currValue == minCountryValue &&
					currIndex < minCountryIndex) {
				minCountry = country;
				minCountryValue = currValue;
				minCountryIndex = currIndex;
			}
		}

		System.out.println("Country #" + minCountry.getId() + " is chosen");

	}

	@Override
	public void attack(GameState state) {
		// I will not attack ^_^
	}

	@Override
	public void transfer(GameState state) {
		// I will not transfer too ^_^
	}
}
