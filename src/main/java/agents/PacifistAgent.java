package agents;

import java.util.ArrayList;

import agents.util.BounsCalculator;
import game.state.ArmyPlacement;
import game.state.Attack;
import game.state.GameState;
import game.util.CountriesPlacementComparator;
import game.world.Country;

public class PacifistAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		// TODO Auto-generated method stub

	}

	private CountriesPlacementComparator comparator;
	private BounsCalculator calculator;
	boolean prevAttack;

	public PacifistAgent() {
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

		// Get the country with the minimum number of soldiers
		Country minCountry = countries.get(0);

		for (Country country : countries) {
			if (comparator.compare(country, minCountry) == -1) {
				minCountry = country;
			}
		}

		System.out.println("Country #" + minCountry.getId() + " is chosen to put " + bonusArmies + " soldiers");

		// Perform the actual changes
		ArmyPlacement placement = new ArmyPlacement(minCountry, bonusArmies);
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

		// Get the attack with the minimum damage of soldiers
		Country playerCountry = attacks.get(0).getAttackingCountry();
		Country opponentCountry = attacks.get(0).getAttackedCountry();
		int minDamage = Integer.MAX_VALUE;

		for (Attack attack : attacks) {

			Country currPlayerCountry = attack.getAttackingCountry();
			Country currOpponentCountry = attack.getAttackedCountry();
			int currDamage = currOpponentCountry.getArmiesSize();

			// Change the state if a lowering can be achieved in the damage
			if (currDamage < minDamage) {
				playerCountry = currPlayerCountry;
				opponentCountry = currOpponentCountry;
				minDamage = currDamage;

				// if no lowering can be achieved in the damage
			} else if (currDamage == minDamage) {

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

		int transfereCount = (playerCountry.getArmiesSize() - opponentCountry.getArmiesSize()) / 2;

		System.out.println("Country #" + playerCountry.getId() + " is attacking Country #" + opponentCountry.getId()
				+ " with transfere of " + transfereCount);

		// Perform the actual changes
		Attack attack = new Attack(playerCountry, opponentCountry);
		attack.setArmyTransferCount(transfereCount);
		state.applyAttack(attack);
	}

	@Override
	public void transfer(GameState state) {

	}

}
