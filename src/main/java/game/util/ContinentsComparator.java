package game.util;

import java.util.Comparator;

import game.world.Continent;

public class ContinentsComparator implements Comparator<Continent> {
	private static ContinentsComparator instance = null;
	private ContinentsComparator() {}

	public static ContinentsComparator getInstance() {
		if (instance == null) {
			instance = new ContinentsComparator();
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
	public int compare(Continent continent1, Continent continent2) {
		if (continent1.getContinentID() < continent2.getContinentID()) {
			return -1;
		} else if (continent1.getContinentID() > continent2.getContinentID()) {
			return 1;
		}
		return 0;
	}

}
