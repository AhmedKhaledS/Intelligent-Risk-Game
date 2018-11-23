package agents;

import java.util.ArrayList;

import game.state.GameState;
import game.util.CountriesPlacementComparator;
import game.world.Country;

public class PassiveAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		// TODO Auto-generated method stub

	}

	private CountriesPlacementComparator comparator;

	public PassiveAgent() {
		comparator = CountriesPlacementComparator.getInstance();
	}

	@Override
	public void place(GameState state) {

		// Get the owned countries from the "GameState" class
		ArrayList<Country> countries = state.getOwnedCountries();

		// Get the country with the minimum number of soldiers
		Country minCountry = countries.get(0);

		for (Country country : countries) {
			if (comparator.compare(country, minCountry) == -1) {
				minCountry = country;
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
