package agents.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import game.Player;
import game.state.GameState;
import game.world.Continent;
import game.world.Country;

public class BounsCalculator {

	private static BounsCalculator instance = null;

	private BounsCalculator() {
	}

	public static BounsCalculator getInstance() {
		if (instance == null) {
			instance = new BounsCalculator();
		}
		return instance;
	}

	/**
	 * 
	 * 
	 */
	public int getBonus(GameState state, boolean prevAttack) {

		// Check if the player have attacked in the previous turn or not
		int prevAttackBouns = prevAttack ? 2 : 0;

		// Calculate the number of owned countries
		ArrayList<Country> countries = state.getOwnedCountries();
		int countriesCount = countries.size();
		int countriesBouns = Math.max(3, countriesCount / 3);

		// Calculate the bonus of the continent
		Player currentPlayer = state.getPlayerTurn();
		Set<Integer> visitedContinent = new HashSet<>();
		int continentBonus = 0;

		for (Country country : countries) {
			Continent continent = country.getContinent();
			if (continent.getContinentOwner() == currentPlayer
					&& !visitedContinent.contains(continent.getContinentID())) {
				continentBonus += continent.getContinentBonus();
				visitedContinent.add(continent.getContinentID());
			}
		}

		return prevAttackBouns + countriesBouns + continentBonus;
	}

}
