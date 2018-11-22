package agents;

import java.util.ArrayList;

import game.Player;
import game.state.Attack;
import game.state.GameState;
import game.world.Continent;
import game.world.Country;

public class AggressiveAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void place(GameState state) {

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

	@Override
	public void attack(GameState state) {

		ArrayList<Attack> attacks = state.getLegalCountriesAttack();

		Continent maxContinent = getMaxContinent(state, attacks);

		int playerCountryIndex = -1;
		int opponentCountryIndex = -1;
		int maxDamage = Integer.MAX_VALUE;

		for (Attack attack : attacks) {
			Country playerCountry = attack.getAttackingCountry();
			Country opponentCountry = attack.getAttackedCountry();
			int currDamage = playerCountry.getArmiesSize() - opponentCountry.getArmiesSize();
			if (currDamage > maxDamage) {

				if (maxContinent != null && !maxContinent.equals(opponentCountry.getContinent())) {
					continue;
				}

				playerCountryIndex = playerCountry.getIndex();
				opponentCountryIndex = opponentCountry.getIndex();
				maxDamage = currDamage;
			}
		}

		System.out.println("Country" + playerCountryIndex +
				"is attacking Country" + opponentCountryIndex);
	}

	@Override
	public void transfer(GameState state) {

	}

	private Continent getMaxContinent(GameState state, ArrayList<Attack> attacks) {

		Player currentPlayer = state.getPlayerTurn(), opponentPlayer;

		if (currentPlayer == Player.PLAYER_1) {
			opponentPlayer = Player.PLAYER_2;
		} else {
			opponentPlayer = Player.PLAYER_1;
		}

		Continent maxContinent = null;
		int maxContinentValue = Integer.MIN_VALUE;

		for (Attack attack : attacks) {
			Continent continent = attack.getAttackedCountry().getContinent();
			if (continent.getContinentOwner() == opponentPlayer &&
					continent.getContinentBonus() > maxContinentValue) {
				maxContinent = continent;
				maxContinentValue = continent.getContinentBonus();
			}
		}

		return maxContinent;
	}

}
