package au.com.mason.expenseManager.factory.recurring;

import au.com.mason.expenseManager.domain.recurring.IncomeLineRecurringItem;
import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.domain.type.IncomeType;
import au.com.mason.expenseManager.form.RecurringItemForm;
import au.com.mason.expenseManager.service.recurring.RecurringItemService;
import au.com.mason.expenseManager.service.type.IncomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeLineRecurringItemProcessor
  extends BaseRecurringItemProcessor
{
  @Autowired
  private IncomeTypeService incomeTypeService;
  
  protected RecurringItem getItem(RecurringItemForm recurringItemForm)
  {
    IncomeLineRecurringItem incomeLineRecurringItem = null;
    if (recurringItemForm.getId() != null) {
      incomeLineRecurringItem = (IncomeLineRecurringItem)this.recurringItemService.findById(recurringItemForm.getId());
    } else {
      incomeLineRecurringItem = new IncomeLineRecurringItem();
    }
    return incomeLineRecurringItem;
  }
  
  protected void process(RecurringItem recurringItem, RecurringItemForm recurringItemForm)
  {
    IncomeLineRecurringItem incomeLineRecurringItem = (IncomeLineRecurringItem)recurringItem;
    incomeLineRecurringItem.setIncomeType((IncomeType)this.incomeTypeService.findById(recurringItemForm.getRecurringItemType()));
  }
}

