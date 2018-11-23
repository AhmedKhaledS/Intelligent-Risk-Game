package agents.intelligent.heuristics;

import java.util.ArrayList;

import game.Player;
import game.state.GameState;
import game.world.Continent;
import game.world.Country;

public class UnclaimedTerritoriesHeuristic implements Heuristic {

	@Override
	public int getHeuristicValue(GameState state) {
		Player player = state.getPlayerTurn();
		ArrayList<Continent> continents = state.getWorldState().getContinents();
		int unclaimedTerritories = 0;
		for (Continent continent : continents) {
			for (Country country : continent.getCountires()) {
				if (country.getOwner() != player) {
					unclaimedTerritories++;
				}
			}
		}
		return unclaimedTerritories;
	}

}
