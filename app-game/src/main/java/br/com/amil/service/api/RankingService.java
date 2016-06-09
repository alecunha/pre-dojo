package br.com.amil.service.api;

import br.com.amil.model.Game;
import br.com.amil.model.Gun;
import br.com.amil.model.Match;

public interface RankingService {
	
	Gun winnerFavoriteGun(Match match);
	
	String streak(Match match);

	int countMatchs(Game game);
}
