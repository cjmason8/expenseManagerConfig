package au.com.mason.expenseManager.domain.recurring;

import au.com.mason.expenseManager.domain.line.RentalIncomeLine;
import au.com.mason.expenseManager.domain.type.RentalIncomeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("RENTAL_INCOME")
public class RentalIncomeLineRecurringItem
  extends RecurringItem
{
  @ManyToOne
  @JoinColumn(name="datagrid_type_id")
  private RentalIncomeType rentalIncomeType;
  
  public RentalIncomeType getRentalIncomeType()
  {
    return this.rentalIncomeType;
  }
  
  public RentalIncomeLine createRentalIncomeLine()
  {
    RentalIncomeLine rentalIncomeLine = new RentalIncomeLine();
    rentalIncomeLine.setIncomeType(this.rentalIncomeType);
    rentalIncomeLine.setAmount(this.amount);
    
    return rentalIncomeLine;
  }
  
  public void setRentalIncomeType(RentalIncomeType rentalIncomeType)
  {
    this.rentalIncomeType = rentalIncomeType;
  }
}

