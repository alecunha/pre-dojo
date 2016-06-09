package br.com.amil.facade;

import java.util.List;

import br.com.amil.enums.AwardEnum;
import br.com.amil.model.Game;
import br.com.amil.model.Match;
import br.com.amil.model.Player;
import br.com.amil.service.api.GameService;
import br.com.amil.service.api.ImportFileService;
import br.com.amil.service.api.RankingService;
import br.com.amil.service.impl.GameServiceImpl;
import br.com.amil.service.impl.ImportFileServiceImpl;
import br.com.amil.service.impl.RankingServiceImpl;

public class GameMain {

	public static void main(String[] args) {
		
		ImportFileService importFileService = new ImportFileServiceImpl();
		GameService gs = new GameServiceImpl();
		Game game = gs.play(importFileService.importFile());
		RankingService rs = new RankingServiceImpl();
		
		System.out.println("----------------------------------------");
		System.out.println("Game - "+ rs.countMatchs(game) +" matchs");
		System.out.println("----------------------------------------");
		for (Match match : game.getMatchs()){
			System.out.println("Ranking - Match:"+ match.getId());
			System.out.println("Winner's Favorite Gun: "+ rs.winnerFavoriteGun(match).getName());
			System.out.println("Streak: "+ rs.streak(match));
			System.out.println("----------------------------------------");
			int i = 1;
			for (Player player :match.getMapRanking().keySet()) {
				System.out.println(i+ "º");
				List<AwardEnum> listAward = match.getAwards().get(player);
				System.out.println(" Player: " + player.getName());
				System.out.println(" Kills: " + match.getMapRanking().get(player).getCountKilling());
				System.out.println(" Deaths: " + match.getMapRanking().get(player).getCountDying());
				System.out.println(" Award: " + (listAward==null? 0 : listAward.size()));
				System.out.println("----------------------------------------");
				i++;
			}
		}
	}

}
