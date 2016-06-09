package br.com.amil.enums;

public enum ActionEnum {
	
	STARTED ("has started"),
	ENDED("has ended"),
	KILLED("killed"),
	SHOOT("using"),
	DROWN("by DROWN");
	
	private String text;
	
	private ActionEnum(String text) {
		this.text= text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
