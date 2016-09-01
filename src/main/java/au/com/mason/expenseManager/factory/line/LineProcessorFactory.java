package au.com.mason.expenseManager.factory.line;

import au.com.mason.expenseManager.domain.Discriminator;
import java.security.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineProcessorFactory
{
  @Autowired
  private ExpenseLineProcessor expenseLineProcessor;
  @Autowired
  private IncomeLineProcessor incomeLineProcessor;
  @Autowired
  private RentalExpenseLineProcessor rentalExpenseLineProcessor;
  @Autowired
  private RentalIncomeLineProcessor rentalIncomeLineProcessor;
  
  public LineProcessor getCreator(Discriminator discriminator)
  {
    if (discriminator.equals(Discriminator.EXPENSE)) {
      return this.expenseLineProcessor;
    }
    if (discriminator.equals(Discriminator.INCOME)) {
      return this.incomeLineProcessor;
    }
    if (discriminator.equals(Discriminator.RENTAL_EXPENSE)) {
      return this.rentalExpenseLineProcessor;
    }
    if (discriminator.equals(Discriminator.RENTAL_INCOME)) {
      return this.rentalIncomeLineProcessor;
    }
    throw new InvalidParameterException("No processor for " + discriminator);
  }
}

