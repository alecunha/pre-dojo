package br.com.amil.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2772677476166531030L;

	private String name;

	private Map<Gun, Integer> rankingGuns = new HashMap<Gun, Integer>();
	
	public String getName() {
		return name;
	}

	public Player setName(String name) {
		this.name = name;
		return this;
	}
	
	public Map<Gun, Integer> getRankingGuns() {
		return rankingGuns;
	}

	public void setRankingGuns(Map<Gun, Integer> rankingGuns) {
		this.rankingGuns = rankingGuns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
