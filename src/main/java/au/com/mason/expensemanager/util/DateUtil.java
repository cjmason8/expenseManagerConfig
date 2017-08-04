package au.com.mason.expensemanager.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static LocalDate getMonday(LocalDate date) {
		return date.with(DayOfWeek.MONDAY);
	}
	
	public static LocalDate getFormattedDate(String date) {
		ZonedDateTime createdAtUTC = ZonedDateTime.parse(date);
		ZonedDateTime createdAtMelb = createdAtUTC.withZoneSameInstant(ZoneId.of("Australia/Melbourne"));
		
		return createdAtMelb.toLocalDate();
	}
	
	public static String getFormattedDateString(LocalDate date) {
		return FORMATTER.format(date);
	}

}
