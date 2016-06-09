package br.com.amil.test.matcher;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.amil.model.Player;
import br.com.amil.model.Ranking;

public class SuccessRankingDeathsMatcher extends TypeSafeMatcher<Map<Player, Ranking>> {

	public void describeTo(Description desc) {
		desc.appendText("É esperado que Nick tenha morrido 2 vezes");
		
	}

	@Override
	protected boolean matchesSafely(Map<Player, Ranking> mapRanking) {
		Player player = new Player();
		player.setName("Nick");
		Ranking ranking = mapRanking.get(player);
		return ranking.getCountDying()==2;
	}

}
