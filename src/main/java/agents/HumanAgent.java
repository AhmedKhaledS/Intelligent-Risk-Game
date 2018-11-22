package agents;

import java.util.ArrayList;

import game.state.GameState;
import game.world.Country;

public class HumanAgent implements Agent {

	private int bonus = 0;

	@Override
	public void takeTurn(GameState state) {

	}

	private void distribute() {
		
		// Getting the owned countries from the "Game State" class
		ArrayList<Country> countries = new ArrayList<>();

		// Getting the country with the minimum number of soldiers
		int minCountryIndex = -1;
		int minCountryValue = Integer.MAX_VALUE;

		for (Country country : countries) {
			int currValue = country.getArmiesSize();
			if (currValue < minCountryValue) {
//				minCountryIndex = country.getIndex();
				minCountryValue = currValue;
			}
		}

		// Getting the owned countries from the "Game State" class
		// ArrayList<Country> countries = new ArrayList<>();
		
		// Getting the country with the maximum number of soldiers
		int maxCountryIndex = -1;
		int maxCountryValue = Integer.MIN_VALUE;

		for (Country country : countries) {
			int currValue = country.getArmiesSize();
			if (currValue > maxCountryValue) {
//				minCountryIndex = country.getIndex();
				minCountryValue = currValue;
			}
		}

	}

	private void attack() {
		// Getting the owned countries from the "Game State" class
		ArrayList<Country> countries = new ArrayList<>();
		
		int playerCountryIndex = -1;
		int opponentCountryIndex = -1;
		int minDamage = Integer.MAX_VALUE;
		
		for (Country country : countries) {
			int currDamage = country.getArmiesSize();
			if (currDamage < minDamage) {
				playerCountryIndex = country.getArmiesSize();
				opponentCountryIndex = country.getArmiesSize();
				minDamage = currDamage;
			}
		}
	}

	private void transfer() {

	}

}