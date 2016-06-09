package br.com.amil.builder;

import java.io.InputStream;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import br.com.amil.enums.ActionEnum;
import br.com.amil.enums.AwardEnum;
import br.com.amil.enums.LayoutEnum;
import br.com.amil.model.Game;
import br.com.amil.model.Gun;
import br.com.amil.model.Match;
import br.com.amil.model.Player;
import br.com.amil.model.Ranking;
import br.com.amil.util.DateMaskUtil;
import br.com.amil.util.SortMapUtil;

public class GameBuilder {
	
	private Game game = new Game();
	
	private Match match;
	
	public Game builderGame(InputStream inputStream) {
		match = new  Match();
		List<Match> matchs = new ArrayList<Match>();
		Map<Player, Ranking> mapRanking = new LinkedHashMap<Player, Ranking>();
		Map<Player, List<Integer>> streakTemp = new LinkedHashMap<Player, List<Integer>>();
		Map<Player, List<Date>> killEvent = new LinkedHashMap<Player, List<Date>>();

		Scanner scanner = new Scanner(inputStream, "UTF-8");
		scanner.useDelimiter(System.lineSeparator());
		
		while (scanner.hasNext()) {
			String line = scanner.next();
			
			if (line.contains(ActionEnum.STARTED.getText())) {
				match = new  Match();
				mapRanking = new LinkedHashMap<Player, Ranking>();
				streakTemp = new LinkedHashMap<Player, List<Integer>>();
				killEvent = new LinkedHashMap<Player, List<Date>>();
				
				match.setStartDate(DateMaskUtil.getDateEvent(line));
				match.setId(getIdMatch(line));
			} else if (line.contains(ActionEnum.ENDED.getText())) {
				match.setEndDate(DateMaskUtil.getDateEvent(line));
				match = builderMatch().orderRanking(mapRanking)
								      .updateStreak(streakTemp)
							          .updateAwardUnbeaten()
								      .updateAwardMultiKill(killEvent)
								      .execute();
				matchs.add(match);
				game.setMatchs(matchs);
			} else if (line.contains(ActionEnum.KILLED.getText())) {
				Player killerPlayer = getKillerPlayer(line);
				updateRakingKiller(mapRanking, killerPlayer);
				updateListStreakByKillerPlayer(streakTemp, killerPlayer);
				updateKillEvent(killEvent, line, killerPlayer);
				
				Player deadPlayer = getDeadPlayer(line);
				updateRakingDeaths(mapRanking, deadPlayer);
				updateSreakDeadPlayer(streakTemp, deadPlayer);
				
				if (line.contains(ActionEnum.SHOOT.getText())) {
					updateRankingGunsByPlayer(line, killerPlayer);
				}
			}
		}
		scanner.close();
		
		return game;
	}

	private void updateSreakDeadPlayer(Map<Player, List<Integer>> streakTemp, Player deadPlayer) {
		List<Integer> listStreakDead = streakTemp.get(deadPlayer);
		if(listStreakDead != null) {
			listStreakDead.add(0);
		}
		streakTemp.put(deadPlayer, listStreakDead);
	}
	
	private GameBuilder orderRanking(Map<Player, Ranking> mapRanking) {
		SortMapUtil.sortByValue(mapRanking);
		match.setMapRanking(mapRanking);
		return this;
	}
	
	private GameBuilder updateStreak(Map<Player, List<Integer>> streakTemp) {
		Map<Player, Integer> streak= new LinkedHashMap<Player, Integer>();
		for (Player player: streakTemp.keySet()) {
			List<Integer> list = streakTemp.get(player);
			if (list !=null && !list.isEmpty()) {
				Collections.sort(list, Collections.reverseOrder());
				streak.put(player, list.get(0));
			}
			
		}
		SortMapUtil.sortByValue(streak);
		match.setStreak(streak);
		
		return this;
	}

	private void updateKillEvent(Map<Player, List<Date>> killEvent, String line, Player killerPlayer) {
		List<Date> listDateKill = killEvent.get(killerPlayer);
		if (listDateKill == null) {
			listDateKill = new ArrayList<Date>();
			listDateKill.add(DateMaskUtil.getDateEvent(line));
		} else {
			listDateKill.add(DateMaskUtil.getDateEvent(line));
		}
		killEvent.put(killerPlayer, listDateKill);
	}

	private void updateListStreakByKillerPlayer(Map<Player, List<Integer>> streakTemp, Player killerPlayer) {
		List<Integer> listStreakKiller = streakTemp.get(killerPlayer);
		if(listStreakKiller == null) {
			listStreakKiller = new ArrayList<Integer>();
			listStreakKiller.add(1);
		} else {
			int last = listStreakKiller.size()-1;
			int count = listStreakKiller.get(last) + 1;
			listStreakKiller.set(last, count);
		}
		streakTemp.put(killerPlayer, listStreakKiller);
	}

	private GameBuilder updateAwardMultiKill(Map<Player, List<Date>> killEvent) {
		for (Player p : killEvent.keySet()) {
			List<Date> listDateKillEvent = killEvent.get(p);
			for (int i = 0; i < listDateKillEvent.size() && (listDateKillEvent.size()-i) - 5 > -1; i++) {
				Temporal dtInicial = listDateKillEvent.get(i).toInstant();
				Temporal dtFinal = listDateKillEvent.get(i+4).toInstant();
				long seconds = ChronoUnit.SECONDS.between(dtInicial, dtFinal);
				
				if (seconds <= 60) {
					Map<Player, List<AwardEnum>> awards = match.getAwards();
					List<AwardEnum> listAward;
					
					if(match.getAwards() == null) {
						awards = new HashMap<Player, List<AwardEnum>>();
						listAward = new ArrayList<AwardEnum>();
					} else {
						listAward = awards.get(p);
					}
					listAward.add(AwardEnum.MULTIKILL);
					awards.put(p, listAward);
					match.setAwards(awards);
				}
			
			}
		}
		return this;
	}

	private void updateRankingGunsByPlayer(String line, Player killerPlayer) {
		String nameGun = line.split(" ")[LayoutEnum.NAME_GUN.getValue()];
		Gun gun = new Gun(nameGun);
		Integer count = killerPlayer.getRankingGuns().get(gun);
		count = (count == null) ? 1 : count+1; 
		killerPlayer.getRankingGuns().put(gun, count);
	}
	
	private GameBuilder updateAwardUnbeaten() {
		Player winnerPlayer = match.getMapRanking().keySet().iterator().next();
		if (match.getMapRanking().get(winnerPlayer).getCountDying() == 0) {
			
			Map<Player, List<AwardEnum>> awards = match.getAwards();
			List<AwardEnum> listAward;
			
			if(match.getAwards() == null) {
				awards = new HashMap<Player, List<AwardEnum>>();
				listAward = new ArrayList<AwardEnum>();
			} else {
				listAward = awards.get(winnerPlayer);
			}
			listAward.add(AwardEnum.UNBEATEN);
			awards.put(winnerPlayer, listAward);
			match.setAwards(awards);
		}
		return this;
	}

	private void updateRakingKiller(Map<Player, Ranking> mapRanking, Player killerPlayer) {
		Ranking ranking = mapRanking.get(killerPlayer);
		
		if (ranking == null) {
			ranking = new Ranking();
			if (killerPlayer.getName().equals("<WORLD>")) {
				ranking.setCountDying(0);
			} else {
				ranking.setCountKilling(1);
			}
		} else if (!killerPlayer.getName().equals("<WORLD>")) {
			int count = ranking.getCountKilling() + 1;
			ranking.setCountKilling(count);
		}
		
		mapRanking.put(killerPlayer, ranking);
	}
	
	private void updateRakingDeaths(Map<Player, Ranking> mapRanking, Player deadPlayer) {
		Ranking ranking = mapRanking.get(deadPlayer);
		
		if (ranking == null) {
			ranking = new Ranking();
			ranking.setCountDying(1);
		} else {
			int count = ranking.getCountDying() + 1;
			ranking.setCountDying(count);
		}
		
		mapRanking.put(deadPlayer, ranking);
	}

	private Player getDeadPlayer(String line) {
		String name[] = line.split(" ");
		return new Player().setName(name[LayoutEnum.NAME_DEAD_PLAYER.getValue()]);
	}

	private Player getKillerPlayer(String line) {
		String name[] = line.split(" ");
		return new Player().setName(name[LayoutEnum.NAME_KILLER_PLAYER.getValue()]);
	}
	
	private int getIdMatch(String line) {
		return Integer.parseInt(line.split(" ")[LayoutEnum.ID_MACTH.getValue()]);
	}
	
	private Match execute() {
		return match;
	}

	private GameBuilder builderMatch() {
		return this;
	}
}
