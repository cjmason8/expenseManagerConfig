package au.com.mason.expenseManager.dto;

import au.com.mason.expenseManager.domain.Discriminator;
import au.com.mason.expenseManager.domain.line.ExpenseLine;
import au.com.mason.expenseManager.domain.line.IncomeLine;
import au.com.mason.expenseManager.domain.line.RentalExpenseLine;
import au.com.mason.expenseManager.domain.line.RentalIncomeLine;
import java.util.Set;

public class LineResultSet
{
  private long total;
  private int weekBeginningId;
  private Discriminator discriminator;
  private Set<ExpenseLine> expenseLineRows;
  private Set<ExpenseLine> expenseLineFooter;
  private Set<IncomeLine> incomeLineRows;
  private Set<IncomeLine> incomeLineFooter;
  private Set<RentalExpenseLine> rentalExpenseLineRows;
  private Set<RentalExpenseLine> rentalExpenseLineFooter;
  private Set<RentalIncomeLine> rentalIncomeLineRows;
  private Set<RentalIncomeLine> rentalIncomeLineFooter;
  
  public Discriminator getDiscriminator()
  {
    return this.discriminator;
  }
  
  public void setDiscriminator(Discriminator discriminator)
  {
    this.discriminator = discriminator;
  }
  
  public long getTotal()
  {
    return this.total;
  }
  
  public void setTotal(long total)
  {
    this.total = total;
  }
  
  public int getWeekBeginningId()
  {
    return this.weekBeginningId;
  }
  
  public void setWeekBeginningId(int weekBeginningId)
  {
    this.weekBeginningId = weekBeginningId;
  }
  
  public Set<ExpenseLine> getExpenseLineRows()
  {
    return this.expenseLineRows;
  }
  
  public void setExpenseLineRows(Set<ExpenseLine> expenseLineRows)
  {
    this.expenseLineRows = expenseLineRows;
  }
  
  public Set<ExpenseLine> getExpenseLineFooter()
  {
    return this.expenseLineFooter;
  }
  
  public void setExpenseLineFooter(Set<ExpenseLine> expenseLineFooter)
  {
    this.expenseLineFooter = expenseLineFooter;
  }
  
  public Set<IncomeLine> getIncomeLineRows()
  {
    return this.incomeLineRows;
  }
  
  public void setIncomeLineRows(Set<IncomeLine> incomeLineRows)
  {
    this.incomeLineRows = incomeLineRows;
  }
  
  public Set<IncomeLine> getIncomeLineFooter()
  {
    return this.incomeLineFooter;
  }
  
  public void setIncomeLineFooter(Set<IncomeLine> incomeLineFooter)
  {
    this.incomeLineFooter = incomeLineFooter;
  }
  
  public Set<RentalExpenseLine> getRentalExpenseLineRows()
  {
    return this.rentalExpenseLineRows;
  }
  
  public void setRentalExpenseLineRows(Set<RentalExpenseLine> rentalExpenseLineRows)
  {
    this.rentalExpenseLineRows = rentalExpenseLineRows;
  }
  
  public Set<RentalExpenseLine> getRentalExpenseLineFooter()
  {
    return this.rentalExpenseLineFooter;
  }
  
  public void setRentalExpenseLineFooter(Set<RentalExpenseLine> rentalExpenseLineFooter)
  {
    this.rentalExpenseLineFooter = rentalExpenseLineFooter;
  }
  
  public Set<RentalIncomeLine> getRentalIncomeLineRows()
  {
    return this.rentalIncomeLineRows;
  }
  
  public void setRentalIncomeLineRows(Set<RentalIncomeLine> rentalIncomeLineRows)
  {
    this.rentalIncomeLineRows = rentalIncomeLineRows;
  }
  
  public Set<RentalIncomeLine> getRentalIncomeLineFooter()
  {
    return this.rentalIncomeLineFooter;
  }
  
  public void setRentalIncomeLineFooter(Set<RentalIncomeLine> rentalIncomeLineFooter)
  {
    this.rentalIncomeLineFooter = rentalIncomeLineFooter;
  }
  
  public Set getRows()
  {
    if (this.discriminator.equals(Discriminator.EXPENSE)) {
      return this.expenseLineRows;
    }
    if (this.discriminator.equals(Discriminator.INCOME)) {
      return this.incomeLineRows;
    }
    if (this.discriminator.equals(Discriminator.RENTAL_EXPENSE)) {
      return this.rentalExpenseLineRows;
    }
    if (this.discriminator.equals(Discriminator.RENTAL_INCOME)) {
      return this.rentalIncomeLineRows;
    }
    return null;
  }
  
  public Set getFooter()
  {
    if (this.discriminator.equals(Discriminator.EXPENSE)) {
      return this.expenseLineFooter;
    }
    if (this.discriminator.equals(Discriminator.INCOME)) {
      return this.incomeLineFooter;
    }
    if (this.discriminator.equals(Discriminator.RENTAL_EXPENSE)) {
      return this.rentalExpenseLineFooter;
    }
    if (this.discriminator.equals(Discriminator.RENTAL_INCOME)) {
      return this.rentalIncomeLineFooter;
    }
    return null;
  }
}

