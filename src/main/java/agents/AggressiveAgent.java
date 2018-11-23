package agents;

import java.util.ArrayList;

import agents.util.BounsCalculator;
import game.Player;
import game.state.ArmyPlacement;
import game.state.Attack;
import game.state.GameState;
import game.util.CountriesPlacementComparator;
import game.world.Continent;
import game.world.Country;

public class AggressiveAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		// TODO Auto-generated method stub

	}

	private CountriesPlacementComparator comparator;
	private BounsCalculator calculator;
	boolean prevAttack;

	public AggressiveAgent() {
		comparator = CountriesPlacementComparator.getInstance();
		calculator = BounsCalculator.getInstance();
		prevAttack = false;
	}

	@Override
	public void place(GameState state) {

		// Initially, Get the number of bonus armies
		int bonusArmies = calculator.getBonus(state, prevAttack);

		// Get the owned countries from the "GameState" class
		ArrayList<Country> countries = state.getOwnedCountries();

		// Get the country with the maximum number of soldiers
		Country maxCountry = countries.get(0);

		for (Country country : countries) {

			if (country.getArmiesSize() == maxCountry.getArmiesSize()) {
				if (country.getId() < maxCountry.getId()) {
					maxCountry = country;
				}
			} else if (comparator.compare(country, maxCountry) == 1) {
				maxCountry = country;
			}

		}

		System.out.println("Country #" + maxCountry.getId() + " is chosen to put " + bonusArmies + " soldiers");

		// Perform the actual changes
		ArmyPlacement placement = new ArmyPlacement(maxCountry, bonusArmies);
		state.placeArmy(placement);

	}

	@Override
	public void attack(GameState state) {

		// Get the legal attacks from the "GameState" class
		ArrayList<Attack> attacks = state.getLegalCountriesAttack();

		// Check for the possibility of the existence of attack
		if (attacks.size() == 0) {

			// Sending "NULL" attack to the "GameState"
			Attack attack = new Attack(null, null);
			state.applyAttack(attack);

			// Setting "prevAttack" to false denoting failure of attack
			prevAttack = false;

			return;
		}

		prevAttack = true;

		// Get the attack with the maximum damage of soldiers
		Continent maxContinent = getMaxContinent(state, attacks);

		Country playerCountry = attacks.get(0).getAttackingCountry();
		Country opponentCountry = attacks.get(0).getAttackedCountry();
		int maxDamage = Integer.MIN_VALUE;

		for (Attack attack : attacks) {

			if (maxContinent != null && !maxContinent.equals(opponentCountry.getContinent())) {
				continue;
			}

			Country currPlayerCountry = attack.getAttackingCountry();
			Country currOpponentCountry = attack.getAttackedCountry();
			int currDamage = currOpponentCountry.getArmiesSize();

			// Change the state if a raising can be achieved in the damage
			if (currDamage > maxDamage) {
				playerCountry = currPlayerCountry;
				opponentCountry = currOpponentCountry;
				maxDamage = currDamage;

				// if no raising can be achieved in the damage
			} else if (currDamage == maxDamage) {

				// Change the state if a lowering can be achieved in the player country
				if (currPlayerCountry.getId() < playerCountry.getId()) {
					playerCountry = currPlayerCountry;
					opponentCountry = currOpponentCountry;

					// if no lowering can be achieved in the player country
					// Change the state if a lowering can be achieved in the opponent country
				} else if (currPlayerCountry.getId() == playerCountry.getId()) {
					if (currOpponentCountry.getId() < opponentCountry.getId()) {
						playerCountry = currPlayerCountry;
						opponentCountry = currOpponentCountry;
					}
				}

			}
		}

		System.out.println("Country #" + playerCountry.getId() + " is attacking Country #" + opponentCountry.getId());

		// Perform the actual changes
		Attack attack = new Attack(playerCountry, opponentCountry);
		attack.setArmyTransferCount((playerCountry.getArmiesSize() - opponentCountry.getArmiesSize()) / 2);
		state.applyAttack(attack);
	}

	@Override
	public void transfer(GameState state) {

	}

	private Continent getMaxContinent(GameState state, ArrayList<Attack> attacks) {

		// Getting the current player
		Player currentPlayer = state.getPlayerTurn(), opponentPlayer;

		// Getting the opponent player
		opponentPlayer = (currentPlayer == Player.PLAYER_1) ? Player.PLAYER_2 : Player.PLAYER_1;

		Continent maxContinent = null;
		int maxContinentValue = Integer.MIN_VALUE;

		// Get the opponent's continent with the maximum bonus (if exists)
		for (Attack attack : attacks) {
			Continent continent = attack.getAttackedCountry().getContinent();
			if (continent.getContinentOwner() == opponentPlayer && continent.getContinentBonus() > maxContinentValue) {
				maxContinent = continent;
				maxContinentValue = continent.getContinentBonus();
			}
		}

		return maxContinent;
	}

}
