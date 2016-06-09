package br.com.amil.enums;

public enum LayoutEnum {

	END_LINE_DATE_EVENT(19),
	ID_MACTH(5),
	NAME_KILLER_PLAYER(3),
	NAME_DEAD_PLAYER(5),
	NAME_GUN(7);
	
	private int value;
	
	LayoutEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
