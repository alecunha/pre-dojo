package br.com.amil.model;

public class Log {
	
	private String content = "";
	
	public Log createHeaderLog(String idMatch) {
		content+= "23/04/2013 15:34:22 - New match "+idMatch+" has started" + System.lineSeparator();
		return this;
	}

	public Log createFooterLog(String idMatch) {
		content+= "23/04/2013 16:39:22 - Match "+idMatch+" has ended" + System.lineSeparator();
		return this;
	}
	
	public Log createEventLog(String lineEvent) {
		content+=lineEvent + System.lineSeparator();
		return this;
	}
	
	public String execute() {
		return content;
	}
}
