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
import br.com.amil.service.impl.GameServiceImpl;
import br.com.amil.test.matcher.SuccessRankingDeathsMatcher;
import br.com.amil.test.matcher.SuccessRankingKillerMatcher;
import br.com.amil.test.matcher.SuccessRankingKillerMatcherWORLD;
import br.com.amil.util.DateMaskUtil;

public class BuilderMatchTest {

	private Match match;
	
	private Game game;
		
	@Before
	public void init() {
		String log = new Log().createHeaderLog("11348965")
							  .createEventLog("23/04/2013 15:36:04 - Roman killed Nick using M16")
							  .createEventLog("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN")
							  .createFooterLog("11348965")
							  .createHeaderLog("11348967")
							  .createEventLog("23/04/2013 15:36:34 - Roman killed Nick using M16")
							  .createEventLog("23/04/2013 15:37:33 - <WORLD> killed Nick by DROWN")
							  .createFooterLog("11348967")
							  .execute();
		
		InputStream inputStream = new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8));
		
		GameService gs = new GameServiceImpl();
		game = gs.play(inputStream);
		match = game.getMatchs().get(0);
	}
	
	@Test
	public void testBuildMatch() {		
		Assert.assertTrue(game.getMatchs().size()==2);
		Assert.assertTrue(match.getId() == 11348965);
		Assert.assertTrue(match.getStartDate().equals(DateMaskUtil.getDateEvent("23/04/2013 15:34:22")));
		Assert.assertTrue(match.getEndDate().equals(DateMaskUtil.getDateEvent("23/04/2013 16:39:22")));
	}

	@Test
	public void testBuilderRankingDeaths() {
		Assert.assertThat(match.getMapRanking(), new SuccessRankingDeathsMatcher());
	}
	
	@Test
	public void testBuilderRankingKiller() {
		Assert.assertThat(match.getMapRanking(), new SuccessRankingKillerMatcher());
	}
	
	@Test
	public void testBuilderRankingKillerWORLD() {
		Assert.assertThat(match.getMapRanking(), new SuccessRankingKillerMatcherWORLD());
	}
	
}