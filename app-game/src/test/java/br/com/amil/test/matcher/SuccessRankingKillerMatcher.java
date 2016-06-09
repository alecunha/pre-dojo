package br.com.amil.test.matcher;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.amil.model.Player;
import br.com.amil.model.Ranking;

public class SuccessRankingKillerMatcher extends TypeSafeMatcher<Map<Player, Ranking>> {

	public void describeTo(Description desc) {
		desc.appendText("É esperado que Roman tenha matado 1 vez.");
		
	}

	@Override
	protected boolean matchesSafely(Map<Player, Ranking> mapRanking) {
		Player player = new Player();
		player.setName("Roman");
		Ranking ranking = mapRanking.get(player);
		return ranking.getCountKilling()==1;
	}

}
