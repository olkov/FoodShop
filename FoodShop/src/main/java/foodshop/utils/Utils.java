package foodshop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import foodshop.application.WebApplication;

public class Utils {
	public static Date parseDate(String date) throws ParseException {
		return new SimpleDateFormat(WebApplication.dateFormat).parse(date);
	}
	
	public static Date parseSQLDate(String date) throws ParseException {
		return new SimpleDateFormat(WebApplication.dateFormatSQL).parse(date);
	}
}
