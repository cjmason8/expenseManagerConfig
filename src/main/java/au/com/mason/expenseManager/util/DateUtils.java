package au.com.mason.expenseManager.util;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import au.com.mason.expenseManager.domain.recurring.RecurringItem;

public class DateUtils
{
  public static LocalDate getMonday(LocalDate date)
  {
    return date.withDayOfWeek(1);
  }
  
  public static LocalDate getDateFromString(String date)
  {
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
    
    return formatter.parseLocalDate(date);
  }
  
  public static String getDateString(LocalDate date)
  {
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
    
    return formatter.print(date);
  }
  
  public static long getTime(LocalDate date)
  {
    return date.toDate().getTime();
  }
  
  public static int findNumberOfInterestDaysInMonth(LocalDate date)
  {
    LocalDate lastDayOfMonth = date.dayOfMonth().withMaximumValue();
    int numberOfDaysInMonth = lastDayOfMonth.getDayOfMonth();
    if (lastDayOfMonth.getDayOfWeek() == 6) {
      numberOfDaysInMonth--;
    } else if (lastDayOfMonth.getDayOfWeek() == 7) {
      numberOfDaysInMonth -= 2;
    }
    LocalDate lastDayOfPreviousMonth = date.minusMonths(1).dayOfMonth().withMaximumValue();
    if (lastDayOfPreviousMonth.getDayOfWeek() == 6) {
      numberOfDaysInMonth++;
    } else if (lastDayOfPreviousMonth.getDayOfWeek() == 7) {
      numberOfDaysInMonth += 2;
    }
    return numberOfDaysInMonth;
  }
  
  public static int getNumberDaysInYear(LocalDate date)
  {
    return date.year().isLeap() ? 366 : 365;
  }
  
  public static LocalDate getLastInterestDayOfMonth(LocalDate date)
  {
    LocalDate lastInterestDayOfMonth = date.dayOfMonth().withMaximumValue();
    if (lastInterestDayOfMonth.getDayOfWeek() == 6) {
      lastInterestDayOfMonth = lastInterestDayOfMonth.minusDays(1);
    } else if (lastInterestDayOfMonth.getDayOfWeek() == 7) {
      lastInterestDayOfMonth = lastInterestDayOfMonth.minusDays(2);
    }
    return lastInterestDayOfMonth;
  }
  
  public static LocalDate getDateForWeek(RecurringItem recurringItem, LocalDate weekBeginningDate)
  {
    int month = weekBeginningDate.getMonthOfYear();
    int dayOfMonth = recurringItem.getStartDate().getDayOfMonth();
    int year = weekBeginningDate.getYear();
    boolean isLeapYear = weekBeginningDate.year().isLeap();
    if (((recurringItem.getStartDate().getDayOfMonth() == 31) || (recurringItem.getStartDate().getDayOfMonth() == 30)) && (month == 2))
    {
      if (isLeapYear) {
        dayOfMonth = 29;
      } else {
        dayOfMonth = 28;
      }
    }
    else if ((recurringItem.getStartDate().getDayOfMonth() == 29) && (month == 2) && (!isLeapYear)) {
      dayOfMonth = 28;
    } else if ((recurringItem.getStartDate().getDayOfMonth() == 31) && ((month == 9) || (month == 4) || (month == 6) || (month == 11))) {
      dayOfMonth = 30;
    }
    if ((dayOfMonth < 7) && (weekBeginningDate.getDayOfMonth() > 25)) {
      if (month < 12)
      {
        month++;
      }
      else
      {
        month = 1;
        year++;
      }
    }
    return new LocalDate(year, month, dayOfMonth);
  }
}

