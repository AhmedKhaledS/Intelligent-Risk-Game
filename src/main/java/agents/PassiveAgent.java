package agents;

import java.util.ArrayList;

import agents.util.BounsCalculator;
import game.state.ArmyPlacement;
import game.state.GameState;
import game.util.CountriesPlacementComparator;
import game.world.Country;

public class PassiveAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		// TODO Auto-generated method stub

	}

	private CountriesPlacementComparator comparator;
	private BounsCalculator calculator;

	public PassiveAgent() {
		comparator = CountriesPlacementComparator.getInstance();
		calculator = BounsCalculator.getInstance();
	}

	@Override
	public void place(GameState state) {

		// Initially, Get the number of bonus armies
		int bonusArmies = calculator.getBonus(state, false);

		// Get the owned countries from the "GameState" class
		ArrayList<Country> countries = state.getOwnedCountries();

		// Get the country with the minimum number of soldiers
		Country minCountry = countries.get(0);

		for (Country country : countries) {
			if (comparator.compare(country, minCountry) == -1) {
				minCountry = country;
			}
		}

		System.out.println("Country #" + minCountry.getId() +
				" is chosen to put " + bonusArmies + " soldiers");
		
		// Perform the actual changes
//		ArmyPlacement placement = new ArmyPlacement(minCountry, bonusArmies);
//		state.placeArmy(placement);

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
