package br.com.amil.service.impl;

import br.com.amil.model.Game;
import br.com.amil.model.Gun;
import br.com.amil.model.Match;
import br.com.amil.model.Player;
import br.com.amil.service.api.RankingService;

public class RankingServiceImpl implements RankingService {
	
	@Override
	public Gun winnerFavoriteGun(Match match) {
		Player player = match.getMapRanking().keySet().iterator().next();
		return player.getRankingGuns().keySet().iterator().next();
	}

	@Override
	public String streak(Match match) {
		Player player = match.getStreak().keySet().iterator().next();
		int countKill = match.getStreak().get(player);
		return player.getName() + " - " + countKill + (countKill > 1 ? " kills" : " kill");
	}

	@Override
	public int countMatchs(Game game) {
		return game.getMatchs().size();
	}
}
