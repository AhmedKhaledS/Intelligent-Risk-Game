package agents;

import java.util.ArrayList;

import game.state.Attack;
import game.state.GameState;
import game.world.Country;

public class PacifistAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void place(GameState state) {

		// Get the owned countries from the "GameState" class
		ArrayList<Country> countries = state.getOwnedCountries();

		// Get the country with the minimum number of soldiers
		int minCountryIndex = -1;
		int minCountryValue = Integer.MAX_VALUE;

		for (Country country : countries) {
			int currValue = country.getArmiesSize();
			if (currValue < minCountryValue) {
				minCountryIndex = country.getId();
				minCountryValue = currValue;
			}
		}

		System.out.println("Country #" + minCountryIndex + "is chosen");

	}

	@Override
	public void attack(GameState state) {

		ArrayList<Attack> attacks = state.getLegalCountriesAttack();

		int playerCountryIndex = -1;
		int opponentCountryIndex = -1;
		int minDamage = Integer.MAX_VALUE;

		for (Attack attack : attacks) {
			Country playerCountry = attack.getAttackingCountry();
			Country opponentCountry = attack.getAttackedCountry();
			int currDamage = playerCountry.getArmiesSize() - opponentCountry.getArmiesSize();
			if (currDamage < minDamage) {
				playerCountryIndex = playerCountry.getId();
				opponentCountryIndex = opponentCountry.getId();
				minDamage = currDamage;
			}
		}

		System.out.println("Country" + playerCountryIndex +
				"is attacking Country" + opponentCountryIndex);
	}

	@Override
	public void transfer(GameState state) {

	}

}
