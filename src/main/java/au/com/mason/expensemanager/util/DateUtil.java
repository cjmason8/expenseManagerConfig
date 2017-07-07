package au.com.mason.expensemanager.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtil {

	public static LocalDate getMonday(LocalDate date) {
		return date.with(DayOfWeek.MONDAY);
	}
}
