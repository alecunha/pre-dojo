package br.com.amil.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.amil.enums.AwardEnum;

public class Match implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -776427290109459100L;

	private Integer id;
	
	private Map<Player, Ranking> mapRanking;
	
	private Map<Player, Integer> streak;
	
	private Map<Player, List<AwardEnum>> awards;
	
	private Map<Player, List<Date>> killEvent;

	private Date startDate;
	
	private Date endDate;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	} 

	public Map<Player, Ranking> getMapRanking() {
		return mapRanking;
	}

	public Match setMapRanking(Map<Player, Ranking> mapRanking) {
		this.mapRanking = mapRanking;
		return this;
	}

	public Map<Player, Integer> getStreak() {
		return streak;
	}

	public void setStreak(Map<Player, Integer> streak) {
		this.streak = streak;
	}

	public Map<Player, List<AwardEnum>> getAwards() {
		return awards;
	}

	public void setAwards(Map<Player, List<AwardEnum>> awards) {
		this.awards = awards;
	}

	public Map<Player, List<Date>> getKillEvent() {
		return killEvent;
	}

	public void setKillEvent(Map<Player, List<Date>> killEvent) {
		this.killEvent = killEvent;
	}

}
