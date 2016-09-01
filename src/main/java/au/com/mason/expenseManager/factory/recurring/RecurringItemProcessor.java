package au.com.mason.expenseManager.factory.recurring;

import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.form.RecurringItemForm;

public abstract interface RecurringItemProcessor
{
  public abstract RecurringItem create(RecurringItemForm paramRecurringItemForm, WeekBeginning paramWeekBeginning);
}

