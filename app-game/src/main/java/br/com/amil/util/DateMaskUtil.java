package br.com.amil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.amil.enums.LayoutEnum;

public class DateMaskUtil {

	private static final String DATE_MASK = "dd/MM/yyyy HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_MASK);
	
	public static Date getDateEvent(String line) {
		try {
			return sdf.parse(line.substring(0, LayoutEnum.END_LINE_DATE_EVENT.getValue()));
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}
}
