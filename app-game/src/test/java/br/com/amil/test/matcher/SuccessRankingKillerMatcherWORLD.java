package br.com.amil.test.matcher;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.amil.model.Player;
import br.com.amil.model.Ranking;

public class SuccessRankingKillerMatcherWORLD extends TypeSafeMatcher<Map<Player, Ranking>> {

	public void describeTo(Description desc) {
		desc.appendText(
				"Assassinatos realizados pelo <WORLD> devem ser desconsiderados, no entanto, " +
			    "as mortes causadas pelo <WORLD> devem ser consideradas para o jogador que foi morto.");

	}

	@Override
	protected boolean matchesSafely(Map<Player, Ranking> mapRanking) {
		Player player = new Player();
		player.setName("<WORLD>");
		Ranking ranking = mapRanking.get(player);
		return ranking.getCountDying()==0 && ranking.getCountKilling()==0;
	}
}	
