package au.com.mason.expenseManager.factory.type;

import au.com.mason.expenseManager.domain.Discriminator;
import java.security.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeProcessorFactory
{
  @Autowired
  private ExpenseTypeProcessor expenseTypeProcessor;
  @Autowired
  private IncomeTypeProcessor incomeTypeProcessor;
  @Autowired
  private RentalExpenseTypeProcessor rentalExpenseTypeProcessor;
  @Autowired
  private RentalIncomeTypeProcessor rentalIncomeTypeProcessor;
  
  public TypeProcessor getProcessor(Discriminator discriminator)
  {
    if (discriminator.equals(Discriminator.EXPENSE)) {
      return this.expenseTypeProcessor;
    }
    if (discriminator.equals(Discriminator.INCOME)) {
      return this.incomeTypeProcessor;
    }
    if (discriminator.equals(Discriminator.RENTAL_EXPENSE)) {
      return this.rentalExpenseTypeProcessor;
    }
    if (discriminator.equals(Discriminator.RENTAL_INCOME)) {
      return this.rentalIncomeTypeProcessor;
    }
    throw new InvalidParameterException("No processor for " + discriminator);
  }
}

