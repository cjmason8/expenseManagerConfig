package au.com.mason.expenseManager.factory.recurring;

import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.domain.recurring.RentalIncomeLineRecurringItem;
import au.com.mason.expenseManager.domain.type.RentalIncomeType;
import au.com.mason.expenseManager.form.RecurringItemForm;
import au.com.mason.expenseManager.service.recurring.RecurringItemService;
import au.com.mason.expenseManager.service.type.RentalIncomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalIncomeLineRecurringItemProcessor
  extends BaseRecurringItemProcessor
{
  @Autowired
  private RentalIncomeTypeService rentalIncomeTypeService;
  
  protected RecurringItem getItem(RecurringItemForm recurringItemForm)
  {
    RentalIncomeLineRecurringItem rentalIncomeLineRecurringItem = null;
    if (recurringItemForm.getId() != null) {
      rentalIncomeLineRecurringItem = (RentalIncomeLineRecurringItem)this.recurringItemService.findById(recurringItemForm.getId());
    } else {
      rentalIncomeLineRecurringItem = new RentalIncomeLineRecurringItem();
    }
    return rentalIncomeLineRecurringItem;
  }
  
  protected void process(RecurringItem recurringItem, RecurringItemForm recurringItemForm)
  {
    RentalIncomeLineRecurringItem rentalIncomeLineRecurringItem = (RentalIncomeLineRecurringItem)recurringItem;
    rentalIncomeLineRecurringItem.setRentalIncomeType((RentalIncomeType)this.rentalIncomeTypeService.findById(recurringItemForm.getRecurringItemType()));
  }
}

