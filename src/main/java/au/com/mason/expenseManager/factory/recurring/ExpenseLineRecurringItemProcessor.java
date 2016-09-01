package au.com.mason.expenseManager.factory.recurring;

import au.com.mason.expenseManager.domain.recurring.ExpenseLineRecurringItem;
import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.domain.type.ExpenseType;
import au.com.mason.expenseManager.form.RecurringItemForm;
import au.com.mason.expenseManager.service.recurring.RecurringItemService;
import au.com.mason.expenseManager.service.type.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseLineRecurringItemProcessor
  extends BaseRecurringItemProcessor
{
  @Autowired
  private ExpenseTypeService expenseTypeService;
  
  protected RecurringItem getItem(RecurringItemForm recurringItemForm)
  {
    ExpenseLineRecurringItem expenseLineRecurringItem = null;
    if (recurringItemForm.getId() != null) {
      expenseLineRecurringItem = (ExpenseLineRecurringItem)this.recurringItemService.findById(recurringItemForm.getId());
    } else {
      expenseLineRecurringItem = new ExpenseLineRecurringItem();
    }
    return expenseLineRecurringItem;
  }
  
  protected void process(RecurringItem recurringItem, RecurringItemForm recurringItemForm)
  {
    ExpenseLineRecurringItem expenseLineRecurringItem = (ExpenseLineRecurringItem)recurringItem;
    expenseLineRecurringItem.setExpenseType((ExpenseType)this.expenseTypeService.findById(recurringItemForm.getRecurringItemType()));
  }
}

