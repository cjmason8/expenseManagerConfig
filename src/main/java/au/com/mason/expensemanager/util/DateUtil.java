package au.com.mason.expensemanager.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static LocalDate getMonday(LocalDate date) {
		return date.with(DayOfWeek.MONDAY);
	}
	
	public static LocalDate getFormattedDate(String date) {
		return LocalDate.parse(date, FORMATTER);
	}
	
	public static String getFormattedDateString(LocalDate date) {
		return FORMATTER.format(date);
	}

}
