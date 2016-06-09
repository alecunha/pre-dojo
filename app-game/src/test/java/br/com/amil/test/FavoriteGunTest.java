package br.com.amil.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.amil.model.Game;
import br.com.amil.model.Log;
import br.com.amil.model.Match;

import br.com.amil.service.api.GameService;
import br.com.amil.service.api.RankingService;
import br.com.amil.service.impl.GameServiceImpl;
import br.com.amil.service.impl.RankingServiceImpl;

public class FavoriteGunTest {
	
private Match match;

	@Before
	public void init() {
		String log = new Log().createHeaderLog("11348965")
							  .createEventLog("23/04/2013 15:36:04 - Roman killed Nick using AK47")
							  .createEventLog("23/04/2013 15:36:05 - Roman killed Nick using M41")
							  .createEventLog("23/04/2013 15:36:06 - Roman killed Nick using M16")
							  .createEventLog("23/04/2013 15:36:07 - Roman killed Nick using AK47")
							  .createEventLog("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN")
							  .createFooterLog("11348965")
							  .execute();
		
		InputStream inputStream = new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8));
		
		GameService gs = new GameServiceImpl();
		Game game = gs.play(inputStream);
		match = game.getMatchs().get(0); 
	}
	
	@Test
	public void testBonusGun() {		 
		 RankingService rankingService = new RankingServiceImpl();
		 Assert.assertTrue(rankingService.winnerFavoriteGun(match).getName().equals("AK47"));
	}
	
}
