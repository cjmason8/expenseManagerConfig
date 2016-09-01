package au.com.mason.expenseManager.factory.recurring;

import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.form.RecurringItemForm;
import au.com.mason.expenseManager.service.WeekBeginningService;
import au.com.mason.expenseManager.service.recurring.RecurringItemService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRecurringItemProcessor
  implements RecurringItemProcessor
{
  @Autowired
  protected RecurringItemService recurringItemService;
  @Autowired
  private WeekBeginningService weekBeginningService;
  
  public RecurringItem create(RecurringItemForm recurringItemForm, WeekBeginning weekBeginning)
  {
    RecurringItem recurringItem = getItem(recurringItemForm);
    recurringItem.setAmount(recurringItemForm.getAmount());
    recurringItem.setStartDate(recurringItemForm.getStartDate());
    recurringItem.setRecurringPeriod(recurringItemForm.getPeriod());
    recurringItem.setNoDueDateRequired(recurringItemForm.isNoDueDateRequired());
    
    process(recurringItem, recurringItemForm);
    
    this.recurringItemService.update(recurringItem);
    this.weekBeginningService.processRecurringItem(weekBeginning, recurringItem);
    
    return recurringItem;
  }
  
  protected abstract RecurringItem getItem(RecurringItemForm paramRecurringItemForm);
  
  protected abstract void process(RecurringItem paramRecurringItem, RecurringItemForm paramRecurringItemForm);
}

