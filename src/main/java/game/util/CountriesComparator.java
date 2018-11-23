package game.util;

import java.util.Comparator;

import game.world.Country;

public class CountriesComparator implements Comparator<Country> {
	private static CountriesComparator instance = null;
	private CountriesComparator() {}

	public static CountriesComparator getInstance() {
		if (instance == null) {
			instance = new CountriesComparator();
		}
		return instance;
	}
	
	/**
	 * Compare function to serve sorting of countries.
	 * @param country1
	 * @param country2
	 * @return
	 */
	@Override
	public int compare(Country country1, Country country2) {
		if (country1.getId() < country2.getId()) {
			return -1;
		} else if (country1.getId() > country2.getId()) {
			return 1;
		}
		return 0;
	}

}
