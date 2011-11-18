package com.trc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class SimpleDate {

	private static final DateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private static final DateFormat shortDateFormat = new SimpleDateFormat("MMyy");

	public static synchronized String format(Date date) {
		return simpleDateFormat.format(date);
	}

	public static String getDate(Date date) {
		return format(date);
	}

	public static synchronized Date parse(String string) throws ParseException {
		return simpleDateFormat.parse(string);
	}

	public static synchronized Date parseShortDate(String string) throws ParseException {
		return shortDateFormat.parse(string);
	}
}
