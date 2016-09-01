package au.com.mason.expenseManager.factory.recurring;

import au.com.mason.expenseManager.domain.Discriminator;
import java.security.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecurringItemFactory
{
  @Autowired
  private ExpenseLineRecurringItemProcessor expenseLineRecurringItemProcessor;
  @Autowired
  private IncomeLineRecurringItemProcessor incomeLineRecurringItemProcessor;
  @Autowired
  private RentalExpenseLineRecurringItemProcessor rentalExpenseRecurringItemProcessor;
  @Autowired
  private RentalIncomeLineRecurringItemProcessor rentalIncomeRecurringItemProcessor;
  
  public RecurringItemProcessor getProcessor(Discriminator discriminator)
  {
    if (discriminator.equals(Discriminator.EXPENSE)) {
      return this.expenseLineRecurringItemProcessor;
    }
    if (discriminator.equals(Discriminator.INCOME)) {
      return this.incomeLineRecurringItemProcessor;
    }
    if (discriminator.equals(Discriminator.RENTAL_EXPENSE)) {
      return this.rentalExpenseRecurringItemProcessor;
    }
    if (discriminator.equals(Discriminator.RENTAL_INCOME)) {
      return this.rentalIncomeRecurringItemProcessor;
    }
    throw new InvalidParameterException("No processor for " + discriminator);
  }
}

