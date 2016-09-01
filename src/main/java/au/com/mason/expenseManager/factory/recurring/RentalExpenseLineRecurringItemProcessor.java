package au.com.mason.expenseManager.factory.recurring;

import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.domain.recurring.RentalExpenseLineRecurringItem;
import au.com.mason.expenseManager.domain.type.RentalExpenseType;
import au.com.mason.expenseManager.form.RecurringItemForm;
import au.com.mason.expenseManager.service.recurring.RecurringItemService;
import au.com.mason.expenseManager.service.type.RentalExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalExpenseLineRecurringItemProcessor
  extends BaseRecurringItemProcessor
{
  @Autowired
  private RentalExpenseTypeService rentalExpenseTypeService;
  
  protected RecurringItem getItem(RecurringItemForm recurringItemForm)
  {
    RentalExpenseLineRecurringItem rentalExpenseLineRecurringItem = null;
    if (recurringItemForm.getId() != null) {
      rentalExpenseLineRecurringItem = (RentalExpenseLineRecurringItem)this.recurringItemService.findById(recurringItemForm.getId());
    } else {
      rentalExpenseLineRecurringItem = new RentalExpenseLineRecurringItem();
    }
    return rentalExpenseLineRecurringItem;
  }
  
  protected void process(RecurringItem recurringItem, RecurringItemForm recurringItemForm)
  {
    RentalExpenseLineRecurringItem rentalExpenseLineRecurringItem = (RentalExpenseLineRecurringItem)recurringItem;
    rentalExpenseLineRecurringItem.setPrincipal(recurringItemForm.getPrincipal());
    rentalExpenseLineRecurringItem.setInterestRate(recurringItemForm.getRate());
    rentalExpenseLineRecurringItem.setRentalExpenseType((RentalExpenseType)this.rentalExpenseTypeService.findById(recurringItemForm.getRecurringItemType()));
  }
}

