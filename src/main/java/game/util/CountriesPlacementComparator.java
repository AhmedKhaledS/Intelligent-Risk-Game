package game.util;

import java.util.Comparator;

import game.world.Country;

public class CountriesPlacementComparator implements Comparator<Country> {

	private static CountriesPlacementComparator instance = null;

	private CountriesPlacementComparator() {
		// Empty Constructor
	}

	public static CountriesPlacementComparator getInstance() {
		if (instance == null) {
			instance = new CountriesPlacementComparator();
		}
		return instance;
	}

	/**
	 * Compare function to serve comparing of countries the placing phase of the
	 * game.
	 * 
	 * @param country1
	 * @param country2
	 * @return 0 if country1 = country2
	 * @return -1 if country1 < country2
	 * @return +1 if country1 > country2
	 */
	@Override
	public int compare(Country country1, Country country2) {

		if (country1.getArmiesSize() < country2.getArmiesSize())
			return -1;
		else if (country1.getArmiesSize() > country2.getArmiesSize())
			return 1;
		else {
			if (country1.getId() < country2.getId())
				return -1;
			else
				return 1;
		}

	}

}
