package br.com.amil.model;

import java.io.Serializable;

public class Ranking implements Serializable, Comparable<Ranking> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -392753513231435175L;

	private int position;
	
	private Player player;
	
	private int countKilling;
	
	private int countDying;
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getCountKilling() {
		return countKilling;
	}

	public void setCountKilling(int countKilling) {
		this.countKilling = countKilling;
	}

	public int getCountDying() {
		return countDying;
	}

	public void setCountDying(int countDying) {
		this.countDying = countDying;
	}

	@Override
	public int compareTo(Ranking r) {
		if (this.countKilling  > r.countKilling) {
			return -1;
		}
		if (this.countKilling  < r.countKilling) {
			return 1;
		} 
		return 0;
	}
}
