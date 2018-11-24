package agents;

import java.util.ArrayList;
import java.util.Scanner;

import agents.util.BounsCalculator;
import game.state.ArmyPlacement;
import game.state.Attack;
import game.state.GameState;
import game.world.Country;

public class HumanAgent implements Agent {

	@Override
	public void takeTurn(GameState state) {
		place(state);
		attack(state);
	}

	Scanner input;
	private BounsCalculator calculator;
	boolean prevAttack;

	public HumanAgent() {
		input = new Scanner(System.in);
		calculator = BounsCalculator.getInstance();
		prevAttack = false;
	}

	@Override
	public void place(GameState state) {

		// Initially, Get the number of bonus armies
		int bonusArmies = calculator.getBonus(state, prevAttack);

		// Get the owned countries from the "GameState" class
		ArrayList<Country> countries = state.getOwnedCountries();

		// Get the required country from the user
		Country requiredCountry = null;

		boolean countryPlacement = false;
		while (!countryPlacement) {
			System.out.println("Enter the ID of the country:");
			int countryID = input.nextInt();
			requiredCountry = getRequiredCountry(countryID, countries);
			if (requiredCountry != null) {
				countryPlacement = true;
			}
		}

		System.out.println(
				"HUMAN: Country #" + requiredCountry.getId() + " is chosen to put " + bonusArmies + " soldiers");

		// Perform the actual changes
		ArmyPlacement placement = new ArmyPlacement(requiredCountry, bonusArmies);
		state.placeArmy(placement);

	}

	@Override
	public void attack(GameState state) {

		// Get the legal attacks from the "GameState" class
		ArrayList<Attack> attacks = state.getLegalCountriesAttack();

		// Check for the possibility of the existence of attack
		if (attacks.size() == 0) {

			System.out.println("HUMAN: I HAVE NO POSSIBLE ATTACKS");

			// Sending "NULL" attack to the "GameState"
			Attack attack = new Attack(null, null);
			state.applyAttack(attack);

			// Setting "prevAttack" to false denoting failure of attack
			prevAttack = false;

			return;
		}

		// Get the required attack from the user
		Attack requiredAttack = null;

		boolean attackPlacement = false;
		while (!attackPlacement) {
			System.out.println("Enter the ID of the (attacking country, attacked country) or (-1,-1) for SKIP:");

			int attackingCountryID = input.nextInt();
			int attackedCountryID = input.nextInt();

			if (attackedCountryID == -1 && attackedCountryID == -1) {

				System.out.println("HUMAN: I AM SKIPPING MY ATTACK");

				// Sending "NULL" attack to the "GameState"
				Attack attack = new Attack(null, null);
				state.applyAttack(attack);

				// Setting "prevAttack" to false denoting failure of attack
				prevAttack = false;

				return;
			}

			requiredAttack = getRequiredAttack(attackingCountryID, attackedCountryID, attacks);
			if (requiredAttack != null) {
				prevAttack = true;
				attackPlacement = true;
			}
		}

		// Get the required transfer from the user
		int transfereCount = 0;

		boolean transferePlacement = false;
		while (!transferePlacement) {
			System.out.println("Enter the soldiers to be transfered to the attacked country:");
			transfereCount = input.nextInt();
			if (transfereCount >= 1 && (requiredAttack.getAttackingCountry().getArmiesSize()
					- requiredAttack.getAttackedCountry().getArmiesSize() - transfereCount) >= 1) {
				transferePlacement = true;
			}

		}

		System.out.println(
				"HUMAN: Country #" + requiredAttack.getAttackingCountry().getId() + " is attacking Country #"
						+ requiredAttack.getAttackedCountry().getId() + " with transfere of " + transfereCount);

		// Perform the actual changes
		Attack attack = new Attack(requiredAttack.getAttackingCountry(), requiredAttack.getAttackedCountry());
		attack.setArmyTransferCount(transfereCount);
		state.applyAttack(attack);

	}

	@Override
	public void transfer(GameState state) {
		// TODO Auto-generated method stub

	}

	private Country getRequiredCountry(int id, ArrayList<Country> countries) {
		Country requiredCountry = null;
		for (Country country : countries) {
			if (country.getId() == id) {
				requiredCountry = country;
			}
		}
		return requiredCountry;
	}

	private Attack getRequiredAttack(int id1, int id2, ArrayList<Attack> attacks) {
		Attack requiredAttack = null;
		for (Attack attack : attacks) {
			if (attack.getAttackingCountry().getId() == id1 && attack.getAttackedCountry().getId() == id2) {
				requiredAttack = attack;
			}
		}
		return requiredAttack;
	}

}